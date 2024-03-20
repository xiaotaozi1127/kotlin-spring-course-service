package org.example.kotlinspringcourse.entity

import jakarta.persistence.*

@Entity
@Table(name = "courses")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var name: String,
    var category: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    val instructor: Instructor? = null
) {
    override fun toString(): String {
        return "course(id=$id, name=$name, category=$category, instructor=${instructor?.id})"
    }
}

