package com.chenluo.test.kotlin.sharedservice

import org.springframework.stereotype.Service

@Service
class MySharedServiceImpl : MySharedService {
    override fun serve(): Boolean {
        println("MySharedServiceImpl#serve")
        return true
    }
}