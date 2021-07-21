package com.dxc.application.webdemo.logging

import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ControllerAdvice(basePackages = ["com.dxc.application"])
class ResponseLog(private val logService: LogService) : ResponseBodyAdvice<Any> {
    override fun supports(methodParameter: MethodParameter, aClass: Class<out HttpMessageConverter<*>>) = true
    override fun beforeBodyWrite(
        body: Any?,
        methodParameter: MethodParameter,
        mediaType: MediaType,
        aClass: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        logService.logResponse(request , response , body)
        return body
    }
}