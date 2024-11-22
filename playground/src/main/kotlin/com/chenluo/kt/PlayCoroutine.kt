package com.chenluo.kt

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class PlayCoroutine {
    fun run() {
        runBlocking { // this: CoroutineScope
            launch { // launch a new coroutine and continue
                delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
                println("World!") // print after delay
                repeat(100) {
                    launch {
                        fun1(it.toString())
                    }
                }
            }
            println("Hello") // main coroutine continues while a previous one is delayed
        }
        println("dispatch done")
    }

}

fun main() {
    PlayCoroutine().run()
}

suspend fun fun1(str: String) {
    delay(Random().nextLong(100))
    println("[${Thread.currentThread().name}] $str")
}