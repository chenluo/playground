package com.chenluo.test.kotlin;

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
open class App

fun main(args: Array<String>) {
    runApplication<App>(*args)

}

enum class MyEnum {
    _1,
    _2
}