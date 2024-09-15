package com.chenluo.test.kotlin

import org.springframework.beans.factory.DisposableBean
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class DiposableTryBean : DisposableBean {
    private val ts = Instant.now().toEpochMilli()
    private val executor: ExecutorService = Executors.newSingleThreadExecutor { r ->
        Thread(r, "target-executor-1-$ts")
    }

    override fun destroy() {
        println("destroyed $ts")
        executor.shutdown()
        while (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
        }
    }

    @EventListener(ApplicationStartedEvent::class)
    fun startEventListener() {
        println("started $ts")
        executor.submit {
            println("executor hi")
        }

    }
}