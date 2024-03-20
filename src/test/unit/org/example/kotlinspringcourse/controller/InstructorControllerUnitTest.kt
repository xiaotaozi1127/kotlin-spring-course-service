package org.example.kotlinspringcourse.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.example.kotlinspringcourse.dto.InstructorDTO
import org.example.kotlinspringcourse.service.InstructorService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [InstructorController::class])
class InstructorControllerUnitTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorService: InstructorService

    @Test
    fun `add instructor when service return success` () {
        val instructorDTO = InstructorDTO(null, "Doe")
        every { instructorService.addInstructor(instructorDTO) } returns InstructorDTO(1, "Doe")
        val responseBody = webTestClient.post().uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody
        assert(responseBody!!.id == 1)
        assert(responseBody.name == "Doe")
    }
}
