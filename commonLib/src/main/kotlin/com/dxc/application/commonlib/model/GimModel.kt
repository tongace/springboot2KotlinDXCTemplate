package com.dxc.application.commonlib.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class GimHeader(val gimType: String?) {
    var gimDesc: String? = null
    var cdLength: BigDecimal? = null
    var field1Label: String? = null
    var field2Label: String? = null
    var field3Label: String? = null
    var activeFlag: String? = null
    var createdBy: String? = null
    var createdDt: Date? = null
    var modifiedBy: String? = null
    var modifiedDt: Date? = null
    var displayActiveFlag: String? = null
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class GimDetail(val gimType: String?, val gimCd: String?) {
    var gimValue: String? = null
    var field1: String? = null
    var field2: String? = null
    var field3: String? = null
    var activeFlag: String? = null
    var displayActiveFlag: String? = null
    var createdBy: String? = null
    var createdDt: LocalDateTime? = null
    var modifiedBy: String? = null
    var modifiedDt: LocalDateTime? = null
}

