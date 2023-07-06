package com.chenluo.test.kotlin.service

import com.chenluo.test.kotlin.sharedservice.MySharedService
import org.springframework.stereotype.Service

@Service
open class MyServiceImpl(private val mySharedService: MySharedService): MyService {
    private val result = mySharedService.serve()
    override fun serve1(): Boolean {
        println(result)
        println("serve1")
        return mySharedService.serve()
    }
}