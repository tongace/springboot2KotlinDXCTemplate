package com.dxc.application.webdemo.feature.common

import com.dxc.application.commonlib.service.CommonService
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/combo")
class ComboController(private val commonService: CommonService) {
    @GetMapping(value = ["/gimtypecombo"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getGimTypeCombo() = commonService.getGimTypeCombo()
    @GetMapping(value = ["/activeflagcombo"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getActiveFlagCombo() = commonService.getActiveFlagCombo()
}