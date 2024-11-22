package com.chenluo.kt

class AssignmentWhy {

}
data class Dummy(val list: List<String> = emptyList()){
    fun add(str:String) = Dummy(list+ listOf( str))
}

fun main() {
    var instance = Dummy().add("1").add("2")
    println("hello")
    
    instance= instance.add("3")
}