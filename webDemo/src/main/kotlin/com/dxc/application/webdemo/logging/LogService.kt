package com.dxc.application.webdemo.logging

import com.dxc.application.commonlib.util.LoggerDelegate
import com.dxc.application.commonlib.util.toJsonString
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LogService {
    private companion object {
        private val log by LoggerDelegate()
    }

    fun logRequest(request: HttpServletRequest, body: Any?) {
        val stringBuilder = StringBuilder()
        val parameters = buildParameterMap(request)
        stringBuilder.append("REQUEST ")
        stringBuilder.append("method=[").append(request.method).append("] ")
        stringBuilder.append("path=[").append(request.requestURI).append("] ")
        parameters.takeIf { it.isNotEmpty() }?.let {
            stringBuilder.append("parameters=[").append(parameters).append("] ");
        }
        body?.let {
            stringBuilder.append("body=[").append(it.toJsonString()).append("]");
        }
        log.info(stringBuilder.toString());
    }

    fun logResponse(request: HttpServletRequest, response: ServerHttpResponse, body: Any?) {
        val stringBuilder = StringBuilder()
        stringBuilder.append("RESPONSE ");
        stringBuilder.append("method=[").append(request.method).append("] ");
        stringBuilder.append("path=[").append(request.requestURI).append("] ");
        body?.let {
            stringBuilder.append("responseBody=[").append(it.toJsonString()).append("] ");
        }
        log.info(stringBuilder.toString());
    }

    private fun buildParameterMap(request: HttpServletRequest): Map<String, String> {
        var resultMap = mutableMapOf<String, String>()
        var value = ""
        request.parameterNames.toList().forEach {
            resultMap[it] = request.getParameter(it)
        }
        return resultMap;
    }
}