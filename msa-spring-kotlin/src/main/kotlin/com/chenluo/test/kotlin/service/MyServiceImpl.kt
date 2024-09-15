package com.chenluo.test.kotlin.service

import com.chenluo.test.kotlin.entity.DummyData
import com.chenluo.test.kotlin.sharedservice.MySharedService
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

@Service
open class MyServiceImpl(private val mySharedService: MySharedService) : MyService {
//  private val result = mySharedService.serve()

  @Retryable(maxAttempts = 3)
  override fun serve1(): Boolean {
//    println(result)
    println("serve1")
    retryMethod()
    return mySharedService.serve()
  }

  @Recover
  fun recover(e: Exception): Boolean {
    println("recover")
    return true
  }

  fun retryMethod() {
    println("run")
    throw IllegalAccessException("jjj")
  }

  fun test() {
    val dummyData = DummyData(1)
    val copy = dummyData.copy()
  }
}
