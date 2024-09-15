package com.chenluo.test.kotlin

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit


@Disabled
class SimpleKtTest {
    @Test
    fun `test`() {
        val now = Instant.now()
        println(now)
        println(now.truncatedTo(ChronoUnit.DAYS))
        val another = Instant.now()
        println(another)
        println(another.truncatedTo(ChronoUnit.DAYS))
        println(another.truncatedTo(ChronoUnit.DAYS) == now.truncatedTo(ChronoUnit.DAYS))
    }
}