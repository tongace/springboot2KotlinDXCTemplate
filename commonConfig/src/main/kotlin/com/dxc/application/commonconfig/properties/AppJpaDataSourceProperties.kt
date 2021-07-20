package com.dxc.application.commonconfig.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app-jpa")
data class AppJpaDataSourceProperties (
    val driverClassName: String,
    val url: String,
    val username: String,
    val password: String,
    val hibernateDialect: String,
    val database: String,
    val showSql: Boolean
)