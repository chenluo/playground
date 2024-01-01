package com.chenluo.test.kotlin

import java.io.File

class FileTester {
    fun writeFile() {
        val printWriter = File("./test.csv").printWriter()
        printWriter.use { out -> out.println("test line") }
    }
}

fun main() {
    FileTester().writeFile()
}