package com.dxc.application.commonlib.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
data class RestJsonData<T>(
    var rowCount: Int? = null,
    var message: String? = null,
    var data: T? = null
)
