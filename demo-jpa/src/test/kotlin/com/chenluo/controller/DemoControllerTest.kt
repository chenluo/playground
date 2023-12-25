package com.chenluo.controller

import org.junit.jupiter.api.Test
import java.time.Instant

class SimpleTest {
    @Test
    fun test() {
        println(Instant.now())
        println(Instant.parse(Instant.now().toString()))
    }

}
