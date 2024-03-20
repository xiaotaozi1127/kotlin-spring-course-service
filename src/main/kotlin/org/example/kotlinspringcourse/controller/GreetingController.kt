package org.example.kotlinspringcourse.controller

import org.example.kotlinspringcourse.service.GreetingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greeting")
class GreetingController (val greetingService: GreetingService) {

    @GetMapping("/{name}")
    fun fetchGreeting(@PathVariable("name") name: String): String {
        val fetchGreeting = greetingService.fetchGreeting(name)
        return fetchGreeting
    }
}
