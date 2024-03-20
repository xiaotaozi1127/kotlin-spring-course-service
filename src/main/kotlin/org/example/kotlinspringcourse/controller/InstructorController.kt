package org.example.kotlinspringcourse.controller

import jakarta.validation.Valid
import org.example.kotlinspringcourse.dto.InstructorDTO
import org.example.kotlinspringcourse.service.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/instructors")
class InstructorController(val instructorService: InstructorService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addInstructor(@Valid @RequestBody instructorDTO: InstructorDTO): InstructorDTO {
        return instructorService.addInstructor(instructorDTO)
    }
}
