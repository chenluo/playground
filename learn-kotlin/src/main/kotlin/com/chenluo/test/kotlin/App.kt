package com.chenluo.test.kotlin;

interface App {
    fun run()
}

class AppInternal : App {
    override fun run() {
        println("run in AppInternal")
    }
}

class AppDelegation(app: AppInternal) : App by app {
    override fun run() {

        println("run in AppDelegation")
        val arrayList = ArrayList<Integer>()
        arrayList
    }
}

fun main() {

    val appInternal = AppInternal()
    AppDelegation(appInternal).run()

}