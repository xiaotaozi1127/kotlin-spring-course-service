package org.example.kotlinspringcourse.dto

import jakarta.validation.constraints.NotBlank

data class InstructorDTO(
    val id: Int?,
    @get: NotBlank(message = "instructorDTO.name is required")
    val name: String)
