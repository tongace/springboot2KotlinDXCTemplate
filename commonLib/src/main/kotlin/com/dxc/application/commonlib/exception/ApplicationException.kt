package com.dxc.application.commonlib.exception

import java.lang.Exception

data class ApplicationException(val messageCode:String, val param: List<Any>?) : Exception()