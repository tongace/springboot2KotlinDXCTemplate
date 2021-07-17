package com.example.webdemo

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HomeController {
    @GetMapping("/test")
    @ResponseBody
    fun test()="Hello Kotlin"
}