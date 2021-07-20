package com.dxc.application.webdemo.util

import com.dxc.application.commonlib.constants.MessagesConstants
import com.dxc.application.commonlib.exception.ApplicationException
import org.springframework.context.MessageSource
import org.springframework.dao.DataAccessException
import org.springframework.web.servlet.support.RequestContextUtils
import javax.servlet.http.HttpServletRequest

object MessageUtil {
    private fun getCauseErrorMessage(e: Exception) = e.cause?.message ?: e.message

    private fun getErrorMessageOfDataAccessException(
        error: String?,
        messageSource: MessageSource,
        request: HttpServletRequest
    ): String {
        val text = error?.split(":")
        val oraCode = text?.get(0)?.trim() ?: ""
        if (oraCode.equals("ORA-04063", true)) {
            return messageSource.getMessage(
                MessagesConstants.DATABASE_ERROR,
                arrayOf(error),
                RequestContextUtils.getLocale(request)
            )
        } else if (oraCode.equals("ORA-00001", true)) {
            return messageSource.getMessage(
                MessagesConstants.DATA_DUPLICATED,
                arrayOf<String>(),
                RequestContextUtils.getLocale(request)
            )
        } else if (oraCode.equals("IO Error", true) || oraCode.indexOf("Could not get Connection") > -1) {
            return messageSource.getMessage(
                MessagesConstants.DATABASE_NOT_CONNECT,
                arrayOf<String>(),
                RequestContextUtils.getLocale(request)
            )
        }
        return messageSource.getMessage(
            MessagesConstants.DATABASE_ERROR,
            arrayOf(error),
            RequestContextUtils.getLocale(request)
        )
    }

    fun getErrorMessage(messageSource: MessageSource, e: Exception, request: HttpServletRequest): String {
        val error = getCauseErrorMessage(e)
        return when (e) {
            is ApplicationException -> {
                val ae: ApplicationException = e
                messageSource.getMessage(
                    ae.messageCode,
                    ae.param?.toTypedArray(),
                    RequestContextUtils.getLocale(request)
                )
            }
            is DataAccessException -> {
                getErrorMessageOfDataAccessException(error, messageSource, request)
            }
            else -> {
                messageSource.getMessage(
                    MessagesConstants.ERROR_UNDEFINED_ERROR,
                    arrayOf(error),
                    RequestContextUtils.getLocale(request)
                )
            }
        }
    }
}