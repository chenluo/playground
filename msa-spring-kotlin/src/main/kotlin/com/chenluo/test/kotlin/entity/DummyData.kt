package com.chenluo.test.kotlin.entity


data class DummyData(val code: Int):Data {
    override fun resolve() {
        println("DummyData")
    }

}