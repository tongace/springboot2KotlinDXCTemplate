package com.dxc.application.webdemo.exception

import com.dxc.application.commonlib.exception.ApplicationException
import com.dxc.application.commonlib.model.RestJsonData
import com.dxc.application.commonlib.util.LoggerDelegate
import com.dxc.application.webdemo.util.MessageUtil
import org.springframework.context.MessageSource
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

@ControllerAdvice(basePackages = ["com.dxc.application"])
class GlobalExceptionHandler(private val messageSource: MessageSource) {
    private companion object {
        private val log by LoggerDelegate()
    }

    @ExceptionHandler(ApplicationException::class)
    @ResponseBody
    fun applicationExceptionHandler(request: HttpServletRequest, ae: ApplicationException) =
        RestJsonData<String>(
            message = MessageUtil.getErrorMessage(messageSource, ae, request),
            rowCount = null,
            data = null
        ).also {
            log.warn("Application Error with Message code : ${ae.messageCode}")
        }
    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun exceptionHandler(request: HttpServletRequest, ex: Exception)=
        when(ex){
            is ApplicationException -> {
                val ae: ApplicationException = ex
                RestJsonData<String>(
                    message = MessageUtil.getErrorMessage(messageSource, ae, request),
                    rowCount = null,
                    data = null
                ).also {
                    log.warn("Application Error with Message code : ${ae.messageCode}")
                }
            }
            else -> {
                RestJsonData<String>(
                    message = MessageUtil.getErrorMessage(messageSource, ex, request),
                    rowCount = null,
                    data = null
                ).also {
                    log.error(ex.message, ex)
                }
            }
        }
}