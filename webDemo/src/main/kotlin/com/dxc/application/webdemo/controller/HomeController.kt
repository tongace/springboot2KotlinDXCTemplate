package com.dxc.application.webdemo.controller

import com.dxc.application.commonlib.model.Combo
import com.dxc.application.commonlib.util.JsonUtil
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HomeController {
    @GetMapping("/test")
    @ResponseBody
    fun test() = JsonUtil.convertObjectToJacksonString(Combo("", "Select"))

    @GetMapping("/home")
    fun home() = "views/home.html"

    @GetMapping("/home/js/home.js")
    fun js() = "js/home.js"
}