package com.dxc.application.webdemo.feature.common

import com.dxc.application.commonlib.service.CommonService
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
@RequestMapping("/common")
class CommonController(private val commonService: CommonService) {
    @GetMapping("/dbservertime", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getDBTime()  = commonService.getDBServerTime()
}