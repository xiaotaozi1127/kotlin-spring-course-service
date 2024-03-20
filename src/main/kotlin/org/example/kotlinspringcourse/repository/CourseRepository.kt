package org.example.kotlinspringcourse.repository

import org.example.kotlinspringcourse.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository: CrudRepository<Course, Int>{
    fun findByNameContaining(courseName: String): List<Course>

    @Query("SELECT * FROM Courses WHERE name LIKE %?1%", nativeQuery = true)
    fun findCoursesByName(name: String): List<Course>
}

