package com.chenluo.test.kotlin.service

import com.chenluo.test.kotlin.sharedservice.MySharedService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@Disabled
class MyServiceImplTest {
  private val mySharedService: MySharedService = mockk()

  @InjectMockKs private lateinit var myServiceImpl: MyServiceImpl

  init {
    every { mySharedService.serve() }.returns(true)
  }

  @Test
  fun serve1() {
    println()
    assertThrows(IllegalAccessException::class.java) { myServiceImpl.serve1() }
  }
}
