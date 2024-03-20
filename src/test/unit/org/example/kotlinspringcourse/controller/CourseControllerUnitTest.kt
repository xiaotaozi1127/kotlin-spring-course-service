package org.example.kotlinspringcourse.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.example.kotlinspringcourse.dto.CourseDTO
import org.example.kotlinspringcourse.service.CourseService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseService: CourseService

    @Test
    fun deleteCourse() {
        every { courseService.deleteCourse(any()) } just runs

        webTestClient.delete()
            .uri("/v1/courses/1")
            .exchange()
            .expectStatus().isNoContent
            .expectBody().isEmpty
    }

    @Test
    fun updateCourses() {
        every { courseService.updateCourse(any(), any()) } returns CourseDTO(1, "course1", "description1", 1)

        val responseBody = webTestClient.put()
            .uri("/v1/courses/1")
            .bodyValue(CourseDTO(1, "course1", "description1", 1))
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assert(responseBody!!.id == 1)
        assert(responseBody.name == "course1")
        assert(responseBody.category == "description1")
    }

    @Test
    fun getCourses() {
        every { courseService.getAllCourses(any()) } returns listOf(CourseDTO(1, "course1", "description1", 1))

        val responseBody = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assert(responseBody!!.size == 1)
        assert(responseBody[0].id == 1)
        assert(responseBody[0].name == "course1")
        assert(responseBody[0].category == "description1")
    }

    @Test
    fun addCourse() {
        every { courseService.addCourse(any()) } returns CourseDTO(1, "course1", "description1", 1)

        val responseBody = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(CourseDTO(null, "course1", "description1", 1))
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult().responseBody

        assert(responseBody!!.id == 1)
        assert(responseBody.name == "course1")
        assert(responseBody.category == "description1")
    }

    @Test
    fun addCourseValidation() {
        val responseBody = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(CourseDTO(null, "", "description1", 1))
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody
        assert(responseBody!!.contains("courseDto.name is required"))
    }

    @Test
    fun addCourseRuntimeException() {
        every { courseService.addCourse(any()) } throws RuntimeException("Error")
        val responseBody = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(CourseDTO(null, "name", "description1", 1))
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody
        assert(responseBody!!.contains("Error"))
    }
}
