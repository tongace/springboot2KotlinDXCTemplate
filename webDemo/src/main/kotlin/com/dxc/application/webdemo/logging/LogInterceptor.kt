package com.dxc.application.webdemo.logging

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LogInterceptor(private val logService: LogService): HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        request.method.takeIf { it.equals(HttpMethod.GET.name) }?.let {
            logService.logRequest(request, null);
        }
        return true
    }
}