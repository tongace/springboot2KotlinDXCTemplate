package com.dxc.application.commonlib.repository

import com.dxc.application.commonlib.model.Combo
import com.dxc.application.commonlib.model.Common
import com.dxc.application.commonlib.util.LoggerDelegate
import com.dxc.application.commonlib.util.toJsonString
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.stereotype.Repository


@Repository
class CommonRepository : BaseRepository() {
    private companion object {
        private val log by LoggerDelegate()
    }

    fun getDBDateTime() =
        jdbcTemplate?.queryForObject(
            "SELECT SYSDATE as CURRENT_SERVER_DATE_TIME FROM DUAL".also {
                log.info("sql >>>>> $it")
            }, DataClassRowMapper.newInstance(
                Common::class.java
            )
        )

    fun getGimTypeCombo(): MutableList<Combo>? {
        val sql = """
            SELECT GIM_TYPE as value, GIM_TYPE as name 
		    FROM TB_M_GIM_H 
		    ORDER BY GIM_TYPE
        """.trimIndent()
        log.info("sql >>>>> $sql")
        return namedParameterJdbcTemplate?.query(sql, DataClassRowMapper.newInstance(Combo::class.java))
    }

    fun getActiveFlagCombo(activeFlag: String): MutableList<Combo>? {
        val sql = """
            SELECT GIM_CD as value, GIM_CD||':'||GIM_VALUE as name 
            FROM TB_M_GIM_D 
            WHERE GIM_TYPE='ACTIVE_FLAG' 
                AND ACTIVE_FLAG = :activeFlag
            ORDER BY TO_NUMBER(FIELD1)
        """.trimIndent()
        val params = hashMapOf(Pair("activeFlag", activeFlag))
        log.info("sql >>>>> $sql")
        log.info("sql param >>>>> ${params.toJsonString()}")
        return namedParameterJdbcTemplate?.query(sql, params, DataClassRowMapper.newInstance(Combo::class.java))
    }
}