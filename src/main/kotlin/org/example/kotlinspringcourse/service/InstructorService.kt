package org.example.kotlinspringcourse.service

import org.example.kotlinspringcourse.dto.InstructorDTO
import org.example.kotlinspringcourse.entity.Instructor
import org.example.kotlinspringcourse.repository.InstructorRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class InstructorService(val instructorRepository: InstructorRepository) {
    fun addInstructor(instructorDTO: InstructorDTO): InstructorDTO {
        instructorRepository.save(Instructor(null, instructorDTO.name)).also {
            return it.let {
                InstructorDTO(it.id, it.name)
            }
        }
    }

    fun findInstructorById(instructorId: Int?) : Optional<Instructor> {
        return instructorRepository.findById(instructorId!!)
    }
}
