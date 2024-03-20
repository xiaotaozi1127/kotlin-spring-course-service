package org.example.kotlinspringcourse.controller

import jakarta.validation.Valid
import org.example.kotlinspringcourse.dto.CourseDTO
import org.example.kotlinspringcourse.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/courses")
class CourseController(val courseService: CourseService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDTO: CourseDTO): CourseDTO {
        return courseService.addCourse(courseDTO)
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable courseId: Int) {
        courseService.deleteCourse(courseId)
    }

    @GetMapping
    fun getAllCourses(@RequestParam("courseName", required = false) courseName: String?) :
            List<CourseDTO> {
        return courseService.getAllCourses(courseName)
    }

    @PutMapping("/{courseId}")
    fun updateCourse(@RequestBody courseDTO: CourseDTO, @PathVariable courseId: Int): CourseDTO {
        return courseService.updateCourse(courseId, courseDTO)
    }
}
