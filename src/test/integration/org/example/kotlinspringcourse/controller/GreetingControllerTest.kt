package org.example.kotlinspringcourse.controller

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class GreetingControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun testGreeting() {
        val body = webTestClient.get()
            .uri("/v1/greeting/{name}", "John")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()
        Assertions.assertEquals("hello from test profile, John", body.responseBody)
    }
}
