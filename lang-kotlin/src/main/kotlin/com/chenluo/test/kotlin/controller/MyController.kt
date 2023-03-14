package com.chenluo.test.kotlin.controller

import com.chenluo.test.kotlin.service.MyService

class MyController(private val myService: MyService) {
    fun serveByMyService(): Boolean {
        return myService.serve1();
    }
}