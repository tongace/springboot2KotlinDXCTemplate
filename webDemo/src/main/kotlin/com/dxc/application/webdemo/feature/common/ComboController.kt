package com.dxc.application.webdemo.feature.common

import com.dxc.application.commonlib.service.CommonService
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/combo")
class ComboController(private val commonService: CommonService) {
    @GetMapping(value = ["/gimtypecombo"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getGimTypeCombo() = commonService.getGimTypeCombo()
    @GetMapping(value = ["/activeflagcombo"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getActiveFlagCombo() = commonService.getActiveFlagCombo()
}