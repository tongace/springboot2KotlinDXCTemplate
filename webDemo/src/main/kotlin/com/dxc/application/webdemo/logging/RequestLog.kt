package com.dxc.application.webdemo.logging

import com.dxc.application.commonlib.util.LoggerDelegate
import com.dxc.application.commonlib.util.toJsonString
import org.springframework.core.MethodParameter
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpMethod
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
import java.lang.reflect.Type
import javax.servlet.http.HttpServletRequest

@ControllerAdvice(annotations = [Controller::class,RestController::class])
class RequestLog(private val request: HttpServletRequest) : RequestBodyAdviceAdapter() {
    private companion object {
        private val log by LoggerDelegate()
    }

    override fun supports(
        methodParameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>
    ) = true

    override fun afterBodyRead(
        body: Any,
        inputMessage: HttpInputMessage,
        parameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>
    ): Any {
        when(request.method){
            HttpMethod.GET.name -> {
                log.info("API URI: ${request.requestURI}, http method: ${request.method}, parameters : ${request.queryString} ")
            }else -> {
                log.info("API URI: ${request.requestURI}, http method: ${request.method} , request body : ${body.toJsonString()}")
            }
        }
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType)
    }

}