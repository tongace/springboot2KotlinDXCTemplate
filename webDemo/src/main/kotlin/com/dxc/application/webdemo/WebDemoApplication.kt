package com.dxc.application.webdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class WebDemoApplication

fun main(args: Array<String>) {
    runApplication<WebDemoApplication>(*args)
}
