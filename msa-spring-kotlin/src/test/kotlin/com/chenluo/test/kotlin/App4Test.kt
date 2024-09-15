package com.chenluo.test.kotlin

import com.chenluo.test.kotlin.service.MyService
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class App4Test: BaseTest {
  @MockkBean private lateinit var myService: MyService


  @Test
  fun init() {
    println(this.javaClass.name)
  }
}
