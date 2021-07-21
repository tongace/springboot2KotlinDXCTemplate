package com.dxc.application.commonlib.service

import com.dxc.application.commonlib.constants.AppConstants
import com.dxc.application.commonlib.repository.CommonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CommonService(private val commonRepository: CommonRepository) {
    @Transactional(value = "appTx", readOnly = true)
    fun getDBServerTime()  = commonRepository.getDBDateTime()
    @Transactional(value = "appTx", readOnly = true)
    fun getGimTypeCombo() = commonRepository.getGimTypeCombo()
    @Transactional(value = "appTx", readOnly = true)
    fun getActiveFlagCombo() = commonRepository.getActiveFlagCombo(AppConstants.ACTIVE_FLAG_ACTIVE)
}