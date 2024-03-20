package org.example.kotlinspringcourse.repository

import org.example.kotlinspringcourse.entity.Course
import org.example.kotlinspringcourse.repository.CourseRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Test
    fun `should be able to find courses by name`() {
        courseRepository.saveAll(listOf(
            Course(1, "course1", "description1"),
            Course(2, "course2", "description2"),
            Course(3, "learn Java", "description3")
        ))
        val courses = courseRepository.findByNameContaining("course")
        assert(courses.size == 2)
    }

    @Test
    fun `should be able to find courses by name with native query`() {
        courseRepository.saveAll(listOf(
            Course(1, "course1", "description1"),
            Course(2, "course2", "description2"),
            Course(3, "learn Java", "description3")
        ))
        val courses = courseRepository.findCoursesByName("course")
        assert(courses.size == 2)
    }

    @ParameterizedTest
    @MethodSource("courseNameAndSize")
    fun `should be able to find courses by name with parameterized test`(name: String, size: Int) {
        courseRepository.saveAll(listOf(
            Course(1, "course1", "description1"),
            Course(2, "course2", "description2"),
            Course(3, "learn Java", "description3")
        ))
        val courses = courseRepository.findCoursesByName(name)
        assert(courses.size == size)
    }

    companion object {
        @JvmStatic
        fun courseNameAndSize() = listOf(
            arrayOf("course", 2),
            arrayOf("learn", 1)
        )
    }
}
