package com.chenluo

import javassist.ClassPool
import javassist.CtField
import javassist.CtNewMethod


class JavaassistTry {
    fun testFailWithFrozenClass() {
        var cp = ClassPool.getDefault()
        val className = "com.chenluo.javassist.frozen.Dummy"
        val itf = cp.makeInterface(className)
        println(itf.isFrozen)
        itf.freeze()
        println(itf.isFrozen)
        cp.makeInterface(className)
    }
    fun testFailWithDuplicatedClass() {
        var cp = ClassPool.getDefault()
        val className = "com.chenluo.javassist.duplicated.Dummy"
        val newClass = cp.makeClass(className)

        // Add a field
        val field = CtField.make("public String message;", newClass)
        newClass.addField(field)


        // Add a method
        val method = CtNewMethod.make(
            "public void printMessage() { System.out.println(message); }",
            newClass
        )
        newClass.addMethod(method)
        newClass.toClass()
        newClass.toClass()
    }
}

fun main() {
    val javaassistTry = JavaassistTry()
    try {
        javaassistTry.testFailWithFrozenClass()
    } catch (e: Exception) {
        e.printStackTrace()
        println(e)
    }
    try {
        javaassistTry.testFailWithDuplicatedClass()
    } catch (e: Exception) {
        e.printStackTrace()
        println(e)
    }
}