package org.example.kotlinspringcourse.controller

import org.example.kotlinspringcourse.dto.CourseDTO
import org.example.kotlinspringcourse.entity.Course
import org.example.kotlinspringcourse.entity.Instructor
import org.example.kotlinspringcourse.repository.CourseRepository
import org.example.kotlinspringcourse.repository.InstructorRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeAll
    fun beforeAll() {
        instructorRepository.save(Instructor(null, "instructor1"))
    }

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
    }

    @Test
    fun deleteExistingCourse() {
        val instructor = instructorRepository.findById(1).get()
        val course = Course(null, "course1", "description1", instructor)
        courseRepository.save(course)

        webTestClient.delete()
            .uri("/v1/courses/{courseId}", course.id)
            .exchange()
            .expectStatus().isNoContent
            .expectBody().isEmpty
    }

    @Test
    fun updateExistingCourse() {
        val instructor = instructorRepository.findById(1).get()
        courseRepository.save(Course(null, "course1", "description1", instructor))

        val responseBody = webTestClient.put()
            .uri("/v1/courses/1")
            .bodyValue(CourseDTO(1, "course1", "new description", 1))
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assert(responseBody!!.category == "new description")
    }

    @Test
    fun getAllCoursesTest() {
        val instructor = instructorRepository.findById(1).get()
        val courses = listOf(
            Course(null, "course1", "description1", instructor),
            Course(null, "course2", "description2", instructor)
        )
        courseRepository.saveAll(courses)

        val responseBody = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assert(responseBody!!.size == 2)
    }

    @Test
    fun `get courses by name`() {
        val instructor = instructorRepository.findById(1).get()
        val courses = listOf(
            Course(null, "course1", "description1", instructor),
            Course(null, "course2", "description2", instructor)
        )
        courseRepository.saveAll(courses)

        val responseBody = webTestClient.get()
            .uri("/v1/courses?courseName=course1")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assert(responseBody!!.size == 1)
        assert(responseBody[0].name == "course1")
        assert(responseBody[0].category == "description1")
    }

    @Test
    fun addCourseTest() {
        val courseDTO = CourseDTO(null, "course1", "description1", 1)
        val responseBody = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody
        assert(responseBody != null)
        assert(responseBody!!.id != null)
    }
}


