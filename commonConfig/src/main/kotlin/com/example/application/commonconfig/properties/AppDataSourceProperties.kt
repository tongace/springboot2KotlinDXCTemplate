package com.example.application.commonconfig.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app-jdbc")
data class AppDataSourceProperties(
    val driverClassName: String,
    val maximumPoolSize: Int,
    val url: String,
    val username: String,
    val password: String
)