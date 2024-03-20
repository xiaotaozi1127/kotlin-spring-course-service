package org.example.kotlinspringcourse.service

import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GreetingService {
    @Value("\${message}")
    lateinit var message: String

    companion object: KLogging()
    fun fetchGreeting(name: String) : String {
        logger.info( "Fetching greeting for $name")
        return "$message, $name"
    }
}
