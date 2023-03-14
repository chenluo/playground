# kafka

## topics, partitions and brokers
**topic**: a concept about what we read and write to. a topic consists of 1 or more partitions on 1 or more brokers.

**partition**: consists a topic. Conceptly a real queue.

**broker**: kafka cluster node.

```
topics:partitions = 1:N

topcis-partition-1 -> 一条队列

topcis-partition-2 -> 一条队列

topcis-partition-N -> 一条队列
```

严格来说，topics是多条队列的集合。

brokers是所有topics的承载。partitions物理地分布在不同的brokers上。

跟Cassandra不一样， Kafka partitions的数量不超过brokers数量并且不会有同一个topic的不同master partitions被分配到相同的broker上。

partitions的replica factor决定了有多少份副本，每个partition有一个master partition和replica partition。master partition是真正承载读和写操作的partition。

producer往topics里写消息时，是根据message的key往多个partition里写的。java api的KafkaProducer是线程安全的。

consumer 订阅某个topics后，broker会根据consumer的groupId和该group中consumer的个数，分配partition和consumer的对应关系。

例如，

* 一个topic包括3个partitions，某个group有**3**个consumer订阅了这个topic，那这3个consumer都会被分配到1个partition，拉取消息。
* 一个topic包括3个partitions，某个group有**1**个consumer订阅了这个topic，这个consumer就被分配到所有的3个partition，拉取消息。

这个分配的过程是consumer.subscribe()时触发。

单个partition的消息顺序是可以保证的。多个partition的情况不能保证全局顺序，例如，一个consumer订阅了一个多partitions的topic，消费的顺序只是partition内局部有序。

in-flight request参数有影响，>1 时，可能会出现先发出的消息发送失败，之后的成功的情况。因此，错误重试机制的选择跟3中消息消费方式相关。
* 至少消费一次：错误重试，ack=all
* 至多消费一次：错误不重试
* exactly once：kafka和client共同保证。kafka消息保证至少一次并且producer的重试不会造成duplicated message，client端需要保证消息即使接收到两次或者offset被回退而导致重新消费到，该消息不会重复触发处理逻辑。 -》幂等请求

>To turn on this feature and get exactly-once semantics per partition—meaning no duplicates, no data loss, and in-order semantics—configure your producer to set “enable.idempotence=true”.

from [confluent blog][1]



## message: key and value

参照cassandra的partition 模型（consisitency hash ring），key在partitioning的分布式系统种的作用就是确定数据所在的partition。可以说，key是为了实现分布式存储消息和支持多个同属一个group的consumer同时消费该topic的消息的基础。

### null key
kafka允许key和value为null，key为null时，会随机发送到某个partition。

## producer and consumer

## exactly once semantics: idempotency and atomicity

## HA of broker
###
###

## performance: sendfile


[1]:https://www.confluent.io/blog/exactly-once-semantics-are-possible-heres-how-apache-kafka-does-it/