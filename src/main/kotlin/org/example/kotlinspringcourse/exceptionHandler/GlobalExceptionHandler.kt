package org.example.kotlinspringcourse.exceptionHandler

import mu.KLogging
import org.example.kotlinspringcourse.exception.InstructorNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Component
@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    companion object : KLogging()

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.error("MethodArgumentNotValidException: ${ex.message}", ex)
        ex.bindingResult.allErrors.map { it.defaultMessage!! }.sorted().also {
            val body = it.joinToString(",")
            logger.info("errors: $body")
            return ResponseEntity(body, status) }
    }

    @ExceptionHandler(InstructorNotFoundException::class)
    fun handleOtherExceptions(ex: InstructorNotFoundException, request: WebRequest): ResponseEntity<Any> {
        logger.error("Exception: ${ex.message}", ex)
        return ResponseEntity("Instructor Not Found", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleOtherExceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        logger.error("Exception: ${ex.message}", ex)
        return ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
