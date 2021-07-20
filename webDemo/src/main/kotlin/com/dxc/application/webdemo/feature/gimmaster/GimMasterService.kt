package com.dxc.application.webdemo.feature.gimmaster

import com.dxc.application.commonlib.constants.AppConstants
import com.dxc.application.commonlib.model.GimDetail
import com.dxc.application.commonlib.model.GimHeader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GimMasterService(private val gimMasterRepository: GimMasterRepository) {
    @Transactional(value = "appTx", readOnly = true)
    fun getGimHeader(model: GimHeader) = gimMasterRepository.findGimHeader(model)

    @Transactional(value = "appTx", rollbackFor = [Exception::class])
    fun saveGimHeader(model: GimHeader) =
        when (model.mode) {
            AppConstants.MODE_ADD -> {
                gimMasterRepository.saveGimHeader(model)
            }
            else -> {
                gimMasterRepository.updateGimHeader(model)
            }
        }

    @Transactional(value = "appTx", readOnly = true)
    fun getGimDetail(model: GimDetail) = gimMasterRepository.findGimDetail(model)

    @Transactional(value = "appTx", rollbackFor = [Exception::class])
    fun saveGimDetail(model: GimDetail) =
        when (model.mode) {
            AppConstants.MODE_ADD -> {
                gimMasterRepository.saveGimDetail(model)
            }
            else -> {
                gimMasterRepository.updateGimDetail(model)
            }
        }
    @Transactional(value = "appTx", rollbackFor = [Exception::class])
    fun deleteGimDetail(gimData: Array<GimDetail>): Int{
        var deleteRowCount = 0
        gimData.forEach {
            deleteRowCount += gimMasterRepository.deleteGimDetailByKeys(it)?:0
        }
        return deleteRowCount
    }
}