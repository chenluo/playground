# volatile

## JMM

规定了线程之间如何通过内存交互的模型。

## 并发3性质

* 原子性
* 可见性： 一个线程对某个变量的写能被另一个线程读到
* 指令/内存重排序：编译器优化和cpu指令重排，memory reorder -》内存屏障 membar

## volatile in java

volatile能保证：

* 防止指令重排序：包括内存序(load store的重排)
* 可见性

jvm把cache抽象成working memory for each thread。volatile保证了变量的load和store操作直接影响内存。

* load：直接从memory读
* store：直接写到memory
  所有thread对该变量的读写都通过memory，性能有损失。可以按照volatile 读合volatile
  写两种操作讨论。

实际上，working memory对应着cpu的多级缓存和缓存优化过程中引入的一些组件(
storeBuffer, invalidateQueue)。

## cache层级

![cacheHierachy](./out/cache/cacheHierachy.svg)
简化的cache层级图

MESI，基于invalidate的缓存coherence协议，保证在cache中的数据的状态是正确的，

* M(odified)
* E(xclusive)
* S(hared)
* I(nvalid)

保证状态的正确性需要额外的delay，为了降低影响，引入了storeBuffer，invalidate queue(
x86可能没有这个buffer，loadbuffer?)。

### storeBuffer

cpu0修改变量a时，需要发出invalidate message到其他cpus，此时需要等待所有invalidate
acknowledge收到，才会真正store到memory。

storeBuffer是为了避免这次等待，发出message之后，把store操作放入storeBuffer，继续执行后续指令。storeBuffer会在acknowledge收到之后，实际执行store指令。storeBuffer把同步的等待，store异步化了。

引入的问题就是，

* 如果需要load一个在storeBuffer里的变量值，就需要依赖store forwarding技术
* 异步处理导致memory reordering，无法保证内存操作的顺序。（重要）

*storeBuffer可能会满，这个时候就必须同步等待ack了。

### invalidate queue

收到invalidate message后，cpu需要一定时间来处理，这个时间可能不短。invalidate
queue就是为了优化这部分delay的技术。

### memory barrier in general

* load memory barrier: apply 所有在invalidate queue中的所有invalidate
  message，相当于在这个barrier之前的
  invalidate会真正生效。invalidate生效，相当于barrier之后的load不会load到invalid的值 =>
  loadload, loadstore
* store memory barrier: apply
  所有在storeBuffer中的store指令，相当于后续的任意内存操作不会在barrier之前生效。=>
  storestore, storeload

### short conclusion

MESI coherence
protocol让每个cpu知道自己的cache的状态，但不加优化的实现会导致效率问题，引入了storeBuffer和invalidate
queue提升效率的同时引起新的问题，缓存中的数据更新不能及时反应到memory。对于一些操作，我们需要一些同步指令，让多线程正确的读写变量。这些指令就是memory
barrier/fence/。。。

## CPU memory model

对于cache，不同的处理器有不同的模型，或者说按照缓存的一致性划分成weak/strong
memory model。

这里的一致性说的是是否允许memory reordering以及允许哪些类型的reordering。

memory reordering是指指令里的load和store的执行顺序和生效顺序可能不同。目前理解中，是因为存在storeBuffer,
invalidate queue这些异步生效的优化手段，才会出现memory reordering。

memory model越weak，就越可能出现load/store生效的顺序与执行顺序的不同。最strong的model是serialize
memory model，可以简单理解为load/store的指令执行和生效的顺序是一致的。

JMM cookbook里抽象出了4种内存操作模式：

* loadload
* loadstore
* storeload
* storestore

不同的CPU架构对这些内存操作的重排序有不同的规范。

对于变量的读写，有两种保证memory ordering的语义：

* Acquire： 代表从内存读取一个变量的值，并且具有防止后续store/load与当前load重排序的语义。
* Release： 写入变量的值到内存中，具有防止较早的store/load与当前store重排序的语义

arm，采用相对weak的模型，允许各种memory order乱序。
x86，相对strong的模型，

* load：具有acquire语义，相当于loadload，loadstore barrier
* store： 具有release语义，相当于loadstore，storestore barrier

因此在x86上，要实现共享变量的可见性，只需要保证storeload不会重排序即可。在store指令之后插入storeload
memory barrier。在x86指令集中，对应mfence，是full memory barrier。jvm的实现中，c1
jit是使用 lock addl 0 (%rsp)这个noop来代替，效率更高。lock前缀修饰的指令有同步语义。这里的同步可以理解为保证cpu
cache和memory的同步/一致。

refer: https://brooker.co.za/blog/2012/09/10/volatile.html
> Locked operations are atomic with respect to all other memory operations and
> all externally visible events. [...] Locked instructions can be used to
> synchronize data written by one processor and read by another processor.

Even in x86, volatile read is not free but with high variation.

## how volatile works in jvm

在bytecode
level，volatile变量的读写并没有跟一般变量有所区别，找不到membar的存在。在jvm执行时，会有逻辑判断一个变量是否是volatile修饰的，以此在store/load指令前后加入membar。

jvm有多种执行字节码的方式，解释执行(templateInterprater)，JIT执行(包括c1，c2)
。所以对volatile变量的读写，有3部分代码，实现相同的语义。

### interpreter

### c1 JIT (client编译器)

### c2 JIT (server编译器)