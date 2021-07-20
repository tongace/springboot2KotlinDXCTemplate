package com.dxc.application.webdemo.feature.gimmaster

import com.dxc.application.commonlib.constants.MessagesConstants
import com.dxc.application.commonlib.model.GimDetail
import com.dxc.application.commonlib.model.GimHeader
import com.dxc.application.commonlib.model.RestJsonData
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.RequestContextUtils
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/gimmaster")
class GimMasterController(
    private val gimService: GimMasterService,
    private val messageSource: MessageSource
) {
    @GetMapping
    fun initialHTML() = "views/gimmaster.html"

    @GetMapping("/js/gimmaster.js")
    fun initialJS() = "js/gimmaster.js"

    @PostMapping(value = ["/gimheader"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getGimHeader(@RequestBody model: GimHeader?, request: HttpServletRequest): RestJsonData<List<GimHeader>> {
        val returnData = RestJsonData<List<GimHeader>>(rowCount = null)
        model?.let {
            val gimList = gimService.getGimHeader(it)
            if (gimList?.isNotEmpty() == true) {
                returnData.data = gimList
            } else {
                returnData.message = messageSource.getMessage(
                    MessagesConstants.ERROR_MESSAGE_DATA_NOT_FOUND,
                    null,
                    RequestContextUtils.getLocale(request)
                )
            }
        }
        return returnData
    }

    @PutMapping(value = ["/gimheader"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun saveGimHeader(@RequestBody gimHeader: GimHeader?): RestJsonData<String> {
        val returnData = RestJsonData<String>(rowCount = null)
        gimHeader?.let {
            it.createdBy = "csamphao"
            it.modifiedBy = "csamphao"
            returnData.rowCount = gimService.saveGimHeader(gimHeader)
        }
        return returnData
    }

    @PostMapping(value = ["/gimdetail"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getGimDetail(@RequestBody model: GimDetail?, request: HttpServletRequest): RestJsonData<List<GimDetail>> {
        val returnData = RestJsonData<List<GimDetail>>(rowCount = null)
        model?.let {
            val gimDetailList = gimService.getGimDetail(it);
            if (gimDetailList?.isNotEmpty()==true) {
                returnData.data = gimDetailList
            } else {
                returnData.message = messageSource.getMessage(
                    MessagesConstants.ERROR_MESSAGE_DATA_NOT_FOUND,
                    null,
                    RequestContextUtils.getLocale(request)
                )
            }
        }
        return returnData
    }
    @PutMapping(value = ["/gimdetail"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun saveGimDetail(@RequestBody model: GimDetail?) : RestJsonData<String>{
        val returnData = RestJsonData<String>(rowCount = null)
        model?.let {
            it.createdBy = "csamphao"
            it.modifiedBy = "csamphao"
            returnData.rowCount =gimService.saveGimDetail(it);
        }
        return returnData
    }
    @DeleteMapping(value = ["/gimdetail"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun deleteGimDetail(@RequestBody input: Array<GimDetail>?): RestJsonData<String>{
        val returnData = RestJsonData<String>(rowCount = null)
        input?.let {
            returnData.rowCount  =gimService.deleteGimDetail(it)
        }
        return returnData
    }
}