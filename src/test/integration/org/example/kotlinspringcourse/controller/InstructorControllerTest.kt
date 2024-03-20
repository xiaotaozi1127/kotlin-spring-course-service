package org.example.kotlinspringcourse.controller

import org.example.kotlinspringcourse.dto.InstructorDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InstructorControllerTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `add instructor test`() {
        val instructorDTO = InstructorDTO(null, "Doe")
        val responseBody = webTestClient.post().uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody
        assert(responseBody!!.id != null)
        assert(responseBody.name == "Doe")
    }

    @Test
    fun `should add failed when name is empty`() {
        val instructorDTO = InstructorDTO(null, "")
        webTestClient.post().uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isBadRequest
    }
}
