package com.chenluo.test.kotlin

class DataClassServiceImpl {}

data class Outer(val i: DataInner, var ii: PlainInner, val iii: PlainInner) {}

data class DataInner(val dummy: Int) {}

class PlainInner(val dummy: Int) {}

fun main() {
  val outer = Outer(DataInner(1), PlainInner(1), PlainInner(1))
  outer.ii = PlainInner(3)
}
