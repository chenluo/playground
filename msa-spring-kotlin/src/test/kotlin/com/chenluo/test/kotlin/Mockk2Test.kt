package com.chenluo.test.kotlin

import com.chenluo.test.kotlin.service.MyService
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.mockk.coEvery
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class Mockk2Test {
    @MockkBean
    private lateinit var service: MyService
//    @SpykBean
//    private lateinit var diposableTryBean: DiposableTryBean
    @BeforeEach
    fun before() {
        coEvery { service.serve1() } answers {
            false
        }
    }

    @Test
    fun run() {
        service.serve1()
    }
    @Test
    fun run2() {
        service.serve1()
    }
}