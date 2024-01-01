package com.chenluo.test.kotlin

import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry

@SpringBootApplication @EnableRetry open class App

fun main(args: Array<String>) {
  runApplication<App>(*args)
}

@Configuration
@ConfigurationProperties("app.listprop")
open class ListProp : InitializingBean {
  val item: List<String> = mutableListOf()

  override fun afterPropertiesSet() {
    println(item)
  }
}

enum class MyEnum {
  _1,
  _2
}
