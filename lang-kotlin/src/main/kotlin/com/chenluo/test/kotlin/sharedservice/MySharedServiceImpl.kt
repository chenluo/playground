package com.chenluo.test.kotlin.sharedservice

class MySharedServiceImpl : MySharedService {
    override fun serve(): Boolean {
        println("MySharedServiceImpl#serve")
        return true
    }
}