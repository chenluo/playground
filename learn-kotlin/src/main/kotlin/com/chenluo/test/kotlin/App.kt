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
    val map = mapOf(1 to "str")
    val b1 = map.get(1)?.toString()?.contains("s") == true
    var b2 = map.get(1)?.toString()?.equals("str") == true
    b1 && b2

    println(map.get(1)?.toString()?.equals("str"))
    println(map.get(2)?.toString()?.equals("str"))

    println(2 ?: 1)


}