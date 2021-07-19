package com.dxc.application.commonlib.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Combo(
    val value: String?,
    val name: String?
)
