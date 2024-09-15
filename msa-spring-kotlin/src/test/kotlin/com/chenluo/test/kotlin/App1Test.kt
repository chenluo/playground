package com.chenluo.test.kotlin

import com.chenluo.test.kotlin.service.MyService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class App1Test: BaseTest {
  @Autowired private lateinit var myService: MyService


  @Test
  fun init() {
    println(this.javaClass.name)
  }
}
