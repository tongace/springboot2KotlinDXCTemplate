package com.dxc.application.webdemo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import java.nio.charset.StandardCharsets

@Configuration
@PropertySource("classpath:demo-application.properties")
class AppConfig {

    companion object {
        fun propertySourcesPlaceholderConfigurer() = PropertySourcesPlaceholderConfigurer()
    }

    @Bean
    fun messageSource() = ReloadableResourceBundleMessageSource()
        .apply {
            setBasenames(
                "classpath:MessageResources", // For application messages
                "classpath:LabelResources" // For screen labels
            )
            setDefaultEncoding(StandardCharsets.UTF_8.toString())
        }

    @Bean
    fun getValidator() = LocalValidatorFactoryBean()
        .apply {
            setValidationMessageSource(messageSource())
        }

    @Bean
    fun multipartResolver() = CommonsMultipartResolver()
        .apply {
            setDefaultEncoding(StandardCharsets.UTF_8.toString())
        }
}