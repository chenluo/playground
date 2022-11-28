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

//    val appInternal = AppInternal()
//    AppDelegation(appInternal).run()
//    val map = mapOf(1 to "str")
//    val b1 = map.get(1)?.toString()?.contains("s") == true
//    var b2 = map.get(1)?.toString()?.equals("str") == true
//    b1 && b2
//
//    println(map.get(1)?.toString()?.equals("str"))
//    println(map.get(2)?.toString()?.equals("str"))
//
//    println(2 ?: 1)
    for (declaredField in MyEnum::class.java.declaredFields) {
        declaredField.trySetAccessible()
        // https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#get-java.lang.Object-
        declaredField.get(null) // If the underlying field is a static field, the obj argument is ignored; it may be null.
        declaredField.isEnumConstant
        println(declaredField.get(null))
    }

}

enum class MyEnum {
    _1,
    _2
}