package com.dxc.application.webdemo.config

import com.dxc.application.commonconfig.properties.AppDataSourceProperties
import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableConfigurationProperties(AppDataSourceProperties::class)
@EnableTransactionManagement
class AppDatabaseConfig(private val dsProperties: AppDataSourceProperties) {
    @Bean
    @Primary
    @Qualifier("appDataSource")
    fun getDataSource() = BasicDataSource().apply {
            driverClassName = dsProperties.driverClassName
            url = dsProperties.url
            username = dsProperties.username
            password = dsProperties.password
        }

    @Bean
    @Qualifier("appTx")
    fun appTransactionManager() = DataSourceTransactionManager(getDataSource())
}

