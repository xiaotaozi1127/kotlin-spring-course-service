package org.example.kotlinspringcourse.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.example.kotlinspringcourse.service.GreetingService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(GreetingController::class)
@AutoConfigureWebTestClient
class GreetingControllerUnitTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var greetingServiceMock: GreetingService

    @Test
    fun testGreeting() {
        val name = "John"
        every { greetingServiceMock.fetchGreeting(any()) } returns "hello from test profile, John"

        val body = webTestClient.get()
            .uri("/v1/greeting/{name}", name)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()
        Assertions.assertEquals("hello from test profile, John", body.responseBody)
    }
}

