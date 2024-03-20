package org.example.kotlinspringcourse.repository

import org.example.kotlinspringcourse.entity.Instructor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InstructorRepository: CrudRepository<Instructor, Int>{

}
