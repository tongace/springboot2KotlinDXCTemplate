package com.dxc.application.webdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class WebDemoApplication

fun main(args: Array<String>) {
    runApplication<WebDemoApplication>(*args)
}
