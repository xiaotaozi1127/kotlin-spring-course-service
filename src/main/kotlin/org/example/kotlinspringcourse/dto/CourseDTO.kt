package org.example.kotlinspringcourse.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDTO(
    var id: Int?,
    @get: NotBlank(message = "courseDto.name is required")
    val name: String,
    @get: NotBlank(message = "courseDto.category is required")
    val category: String,
    @get: NotNull(message = "courseDto.instructorId can not be null")
    val instructorId: Int?
)

