package com.chenluo.controller

import java.time.Instant
import org.junit.jupiter.api.Test

class SimpleTest {
  @Test
  fun test() {
    println(Instant.now())
    println(Instant.parse(Instant.now().toString()))
  }
}
