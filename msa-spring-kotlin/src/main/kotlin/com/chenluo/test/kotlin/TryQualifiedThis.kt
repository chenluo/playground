package com.chenluo.test.kotlin

class TryQualifiedThis {
  class A { // implicit label @A
    inner class B { // implicit label @B
      fun A.foo() { // implicit label @foo
        val a = this@A // A's this
        val b = this@B // B's this
        a.aFun()
        b.bFun()

        println("A#foo: " + a)
        println("A#foo: " + b)
        val c = this // foo()'s receiver, an Int
        val c1 = this@foo // foo()'s receiver, an Int
        println("A#foo: " + c)
        println("A#foo: " + c1)

        //                val funLit = lambda@ fun String.() {
        //                    val d = this // funLit's receiver, a String
        //                }
        //
        //                val funLit2 = { s: String ->
        //                    // foo()'s receiver, since enclosing lambda expression
        //                    // doesn't have any receiver
        //                    val d1 = this
        //                }
      }

      fun bFun() {
        println("bFun")
      }

      fun callFoo() {
        println("B#callFoo: " + this@B)
        println("B#callFoo: " + this)
        foo()
      }
    }

    fun aFun() {
      println("aFun")
    }

    fun callFoo() {

      println("A#callFoo: " + this@A)
      println("A#callFoo: " + this)
      B().callFoo()

      //            a.foo() // out of scope
    }
  }
}

fun main() {
  //    val num = 1
  ////    TryQualifiedThis.A().callFoo()
  //    num.apply {
  //        println(this@apply == this)
  //    }
  val a = 2.toFloat()
  //    val map = mapOf<String, String>()
  //    map as Map<Any, Any>
  //    val s1 = "String"
  //    val o1 = "String" as Any
  //    assert(s1 == o1)
  //    assert(s1.toString() == o1.toString())
}
