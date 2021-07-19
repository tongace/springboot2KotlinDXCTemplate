package com.dxc.application.commonlib.model

import java.math.BigDecimal

data class RestJsonData<T>(
    val message: String?,
    val rowCount: BigDecimal?,
    val data :T?
)
