package com.dxc.application.commonlib.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

object JsonUtil {
    private val mapper: ObjectMapper = ObjectMapper()
    fun convertObjectToJacksonString(any: Any?) =
       try {
           any?.let {
               mapper.writeValueAsString(it)
           }
       }catch (ex: JsonProcessingException){
           any?.toString()
       }

}