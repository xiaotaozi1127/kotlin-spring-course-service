package org.example.kotlinspringcourse.service

import mu.KLogging
import org.example.kotlinspringcourse.dto.CourseDTO
import org.example.kotlinspringcourse.entity.Course
import org.example.kotlinspringcourse.exception.CourseNotFoundException
import org.example.kotlinspringcourse.exception.InstructorNotFoundException
import org.example.kotlinspringcourse.repository.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository, val instructorService: InstructorService) {

    companion object : KLogging()

    fun addCourse(courseDTO: CourseDTO): CourseDTO {
        val optional = instructorService.findInstructorById(courseDTO.instructorId)
        if(!optional.isPresent) {
            throw InstructorNotFoundException("Instructor not found by Id: ${courseDTO.instructorId}")
        }

        val course = courseDTO.let {
            Course(null, it.name, it.category, optional.get())
        }
        courseRepository.save(course)

        logger.info { "Course saved: $course" }
        return course.let {
            CourseDTO(it.id, it.name, it.category, optional.get().id)
        }
    }

    fun getAllCourses(courseName: String?): List<CourseDTO> {
        val courses = courseName?.let {
            logger.info { "find courses by name: $courseName" }
            courseRepository.findByNameContaining(it)
        } ?: courseRepository.findAll()

        courses.map { course ->
            CourseDTO(course.id, course.name, course.category, course.instructor!!.id)
        }.also {
            logger.info { "Courses found: $it" }
            return it
        }
    }

    fun updateCourse(courseId: Int, courseDTO: CourseDTO): CourseDTO {
        val course = courseRepository.findById(courseId)
        if(course.isPresent) {
            return course.get().let {
                it.name = courseDTO.name
                it.category = courseDTO.category
                courseRepository.save(it)
                logger.info { "Course updated: $it" }
                CourseDTO(it.id, it.name, it.category, it.instructor!!.id)
            }
        } else {
            throw CourseNotFoundException("Course not found by Id: $courseId")
        }
    }

    fun deleteCourse(courseId: Int) {
        courseRepository.existsById(courseId).also {
            if(it) {
                courseRepository.deleteById(courseId)
                logger.info { "Course deleted by Id: $courseId" }
            } else {
                throw CourseNotFoundException("Course not found by Id: $courseId")
            }
        }
    }
}


