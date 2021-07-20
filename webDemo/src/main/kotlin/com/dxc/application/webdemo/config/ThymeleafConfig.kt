package com.dxc.application.webdemo.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring5.view.ThymeleafViewResolver
import org.thymeleaf.templatemode.TemplateMode
import java.nio.charset.StandardCharsets

@Configuration
@EnableWebMvc
class ThymeleafConfig() : ApplicationContextAware, WebMvcConfigurer {
    @Autowired
    private lateinit var thymeleafProperties: ThymeleafProperties
    @Autowired
    private lateinit var springTemplateEngine: SpringTemplateEngine

    private var applicationContext: ApplicationContext? = null
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    @Bean
    fun javascriptThymeleafViewResolver() = ThymeleafViewResolver()
        .apply {
            templateEngine = springTemplateEngine
            characterEncoding = StandardCharsets.UTF_8.toString()
            contentType = "application/javascript"
            viewNames = arrayOf("*.js")
            isCache = thymeleafProperties.isCache
        }

    @Bean
    fun javascriptTemplateResolver() = SpringResourceTemplateResolver()
        .apply {
            order = 1
            applicationContext = this@ThymeleafConfig.applicationContext
            prefix = "WEB-INF/pages/"
            suffix = ".js"
            templateMode = TemplateMode.JAVASCRIPT
            characterEncoding = StandardCharsets.UTF_8.toString()
            checkExistence = true
            isCacheable = thymeleafProperties.isCache
        }

    @Bean
    fun htmlThymeleafViewResolver() = ThymeleafViewResolver()
        .apply {
            templateEngine = springTemplateEngine
            characterEncoding = StandardCharsets.UTF_8.toString()
            contentType = "text/html"
            viewNames = arrayOf("*.html")
            isCache = thymeleafProperties.isCache
        }

    @Bean
    fun htmlTemplateResolver() = SpringResourceTemplateResolver()
        .apply {
            order = 0
            applicationContext = this@ThymeleafConfig.applicationContext
            prefix = "WEB-INF/pages/"
            suffix = ".html"
            templateMode = TemplateMode.HTML
            characterEncoding = StandardCharsets.UTF_8.toString()
            checkExistence = true
            isCacheable = thymeleafProperties.isCache
        }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        with(registry) {
            addResourceHandler("/webjars/**").addResourceLocations("/webjars/").resourceChain(false)
            addResourceHandler("/css/**").addResourceLocations("/WEB-INF/resources/css/")
            addResourceHandler("/scripts/**").addResourceLocations("/WEB-INF/resources/scripts/")
            addResourceHandler("/images/**").addResourceLocations("/WEB-INF/resources/images/")
        }
    }
}