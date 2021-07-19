package com.dxc.application.webdemo.config

import com.example.application.commonconfig.properties.BfwJpaDataSourceProperties
import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.PropertySource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(BfwJpaDataSourceProperties::class)
@EnableTransactionManagement
class BfwJPADatabaseConfig(private val dsProperties: BfwJpaDataSourceProperties) {
    @Bean
    @Qualifier("dataSource_bfw")
    fun getBFWJPADataSource(): DataSource = BasicDataSource()
        .apply {
            driverClassName = dsProperties.driverClassName
            url = dsProperties.url
            username = dsProperties.username
            password = dsProperties.password
        }

    @Bean
    @Qualifier("entityManagerFactory_bfw")
    fun getBFWEntityManagerFactory() = LocalContainerEntityManagerFactoryBean()
        .apply {
            dataSource = getBFWJPADataSource()
            jpaVendorAdapter = HibernateJpaVendorAdapter()
                .apply {
                    setShowSql(dsProperties.showSql)
                    setDatabase(Database.valueOf(dsProperties.database))
                    setJpaProperties(Properties().apply {
                        setProperty("hibernate.dialect", dsProperties.hibernateDialect)
                        setProperty("hibernate.show_sql", dsProperties.showSql.toString())
                    })
                    persistenceUnitName = "st3main_bfw"
                    setPackagesToScan("th.co.toyota.st3.api.model")
                }
        }
    @Primary
    @Bean
    fun transactionManager() = JpaTransactionManager(getBFWEntityManagerFactory().`object`!!)
}