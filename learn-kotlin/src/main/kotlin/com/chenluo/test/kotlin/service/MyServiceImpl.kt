package com.chenluo.test.kotlin.service

import com.chenluo.test.kotlin.sharedservice.MySharedService

open class MyServiceImpl(private val mySharedService: MySharedService): MyService {
    override fun serve1(): Boolean {
        println("serve1")
        return mySharedService.serve()
    }
}