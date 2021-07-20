package com.dxc.application.webdemo.config

import com.dxc.application.commonconfig.properties.AppJpaDataSourceProperties
import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.PropertySource
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(AppJpaDataSourceProperties::class)
@EnableTransactionManagement
class AppJPADatabaseConfig(private val dsProperties: AppJpaDataSourceProperties) {
    @Bean
    @Qualifier("jpaDataSource")
    fun getJPADataSource(): DataSource = BasicDataSource()
        .apply {
            driverClassName = dsProperties.driverClassName
            url = dsProperties.url
            username = dsProperties.username
            password = dsProperties.password
        }

    @Bean
    @Primary
    @Qualifier("entityManagerFactory")
    fun getEntityManagerFactory() = LocalContainerEntityManagerFactoryBean()
        .apply {
            dataSource = getJPADataSource()
            jpaVendorAdapter = HibernateJpaVendorAdapter()
                .apply {
                    setShowSql(dsProperties.showSql)
                    setDatabase(Database.valueOf(dsProperties.database))
                    setJpaProperties(Properties().apply {
                        setProperty("hibernate.dialect", dsProperties.hibernateDialect)
                        setProperty("hibernate.show_sql", dsProperties.showSql.toString())
                    })
                    persistenceUnitName = "st3main"
                    setPackagesToScan(
                        "com.dxc.application.model",
                        "th.co.toyota.st3.api.model"
                    )
                }
        }

    @Primary
    @Bean
    @Qualifier("appJpaTx")
    fun appJpaTransactionManager() = JpaTransactionManager(getEntityManagerFactory().`object`!!)

    @Bean
    fun jpaExceptionTranslation() = PersistenceExceptionTranslationPostProcessor()
}