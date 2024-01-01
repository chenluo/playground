package com.chenluo.test.kotlin.controller

import com.chenluo.test.kotlin.service.MyService
import org.springframework.stereotype.Controller

@Controller
class MyController(private val myService: MyService) {
  fun serveByMyService(): Boolean {
    return myService.serve1()
  }
}
