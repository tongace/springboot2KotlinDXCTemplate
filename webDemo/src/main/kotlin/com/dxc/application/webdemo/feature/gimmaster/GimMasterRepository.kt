package com.dxc.application.webdemo.feature.gimmaster

import com.dxc.application.commonlib.model.GimDetail
import com.dxc.application.commonlib.model.GimHeader
import com.dxc.application.commonlib.repository.BaseRepository
import com.dxc.application.commonlib.util.LoggerDelegate
import com.dxc.application.commonlib.util.toJsonString
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource

class GimMasterRepository : BaseRepository() {
    private companion object {
        private val log by LoggerDelegate()
    }

    fun findGimHeader(model: GimHeader): MutableList<GimHeader>? {
        val sql = StringBuilder(
            """
            SELECT GIM_TYPE,
                GIM_DESC,
                CD_LENGTH,
                FIELD1_LABEL,
                FIELD2_LABEL,
                FIELD3_LABEL,
                ACTIVE_FLAG,
                (SELECT GIM_VALUE
                   FROM TB_M_GIM_D
                  WHERE GIM_TYPE = 'ACTIVE_FLAG' AND GIM_CD = GIMH.ACTIVE_FLAG)
                    AS DISPLAY_ACTIVE_FLAG,
                CREATED_BY,
                CREATED_DT,
                MODIFIED_BY,
                MODIFIED_DT
		    FROM TB_M_GIM_H GIMH 
            WHERE 1=1 
        """.trimIndent()
        )
        model.searchGimTypes?.takeIf { it.isNotEmpty() }?.let {
            sql.append(" AND GIM_TYPE IN ${it.joinToString(prefix = "('", postfix = "')", separator = "','")} ")
        }
        model.searchGimDesc?.takeIf { it.isBlank() }?.let {
            sql.append(" AND UPPER(GIM_DESC) LIKE UPPER(REPLACE(:searchGimDesc,'*','%')) ")
        }
        model.searchActiveFlag?.takeIf { it.isBlank() && it.equals("ALL", true) }?.let {
            sql.append(" AND ACTIVE_FLAG =:searchActiveFlag ")
        }
        sql.append(" ORDER BY GIM_TYPE ")
        log.info("sql >>>>> $sql")
        log.info("sql param >>>>> ${model.toJsonString()}")
        return namedParameterJdbcTemplate?.query(
            sql.toString(),
            BeanPropertySqlParameterSource(model),
            DataClassRowMapper.newInstance(GimHeader::class.java)
        )
    }

    fun findGimHeaderByPrimaryKey(gimType: String): GimHeader? {
        val sql = """
            SELECT GIM_TYPE,
                GIM_DESC,
                CD_LENGTH,
                FIELD1_LABEL,
                FIELD2_LABEL,
                FIELD3_LABEL,
                ACTIVE_FLAG,
                (SELECT GIM_VALUE
                   FROM TB_M_GIM_D
                  WHERE GIM_TYPE = 'ACTIVE_FLAG' AND GIM_CD = GIMH.ACTIVE_FLAG) AS DISPLAY_ACTIVE_FLAG,
                CREATED_BY,
                CREATED_DT,
                MODIFIED_BY,
                MODIFIED_DT
            FROM TB_M_GIM_H GIMH
            WHERE GIM_TYPE = UPPER (:gimType)
    """.trimIndent()
        val params = hashMapOf(Pair("gimType", gimType))
        log.info("sql >>>>> $sql")
        log.info("sql param >>>>> ${params.toJsonString()}")
        return namedParameterJdbcTemplate?.queryForObject(
            sql,
            params,
            DataClassRowMapper.newInstance(GimHeader::class.java)
        )
    }

    fun saveGimHeader(model: GimHeader): Int? {
        val sql = """
            INSERT INTO TB_M_GIM_H (GIM_TYPE,
                        GIM_DESC,
                        CD_LENGTH,
                        FIELD1_LABEL,
                        FIELD2_LABEL,
                        FIELD3_LABEL,
                        ACTIVE_FLAG,
                        CREATED_BY,
                        CREATED_DT,
                        MODIFIED_BY,
                        MODIFIED_DT)
            VALUES (UPPER ( :gimType),
                    :gimDesc,
                    :cdLength,
                    :field1Label,
                    :field2Label,
                    :field3Label,
                    :activeFlag,
                    :createdBy,
                    SYSDATE,
                    :modifiedBy,
                    SYSDATE)
        """.trimIndent()
        log.info("sql >>>>> $sql")
        log.info("sql param >>>>> ${model.toJsonString()}")
        return namedParameterJdbcTemplate?.update(sql, BeanPropertySqlParameterSource(model))
    }

    fun updateGimHeader(model: GimHeader): Int? {
        val sql = """
            UPDATE TB_M_GIM_H
            SET GIM_DESC = :gimDesc,
                CD_LENGTH = :cdLength,
                FIELD1_LABEL = :field1Label,
                FIELD2_LABEL = :field2Label,
                FIELD3_LABEL = :field3Label,
                ACTIVE_FLAG = :activeFlag,
                MODIFIED_BY = :modifiedBy,
                MODIFIED_DT = SYSDATE
            WHERE GIM_TYPE = :gimType 
                AND MODIFIED_DT = :modifiedDt
        """.trimIndent()
        log.info("sql >>>>> $sql")
        log.info("sql param >>>>> ${model.toJsonString()}")
        return namedParameterJdbcTemplate?.update(sql, BeanPropertySqlParameterSource(model))
    }

    fun updateActiveFlagOfGimDetailByGimHeaderActiveFlag(model: GimHeader): Int? {
        val sql = """
            UPDATE TB_M_GIM_D
            SET ACTIVE_FLAG = :activeFlag,
                MODIFIED_BY = :modifiedBy,
                MODIFIED_DT = SYSDATE
            WHERE GIM_TYPE = :gimType
        """.trimIndent()
        log.info("sql >>>>> $sql")
        log.info("sql param >>>>> ${model.toJsonString()}")
        return namedParameterJdbcTemplate?.update(sql, BeanPropertySqlParameterSource(model))
    }

    fun findGimDetail(model: GimDetail): MutableList<GimDetail>? {
        val sql = StringBuilder(
            """
            SELECT GIM_TYPE,
                GIM_CD,
                GIM_VALUE,
                FIELD1,
                FIELD2,
                FIELD3,
                ACTIVE_FLAG,
                (SELECT GIM_VALUE
                    FROM TB_M_GIM_D
                    WHERE GIM_TYPE = 'ACTIVE_FLAG' AND GIM_CD = GIMD.ACTIVE_FLAG) AS DISPLAY_ACTIVE_FLAG,
                CREATED_BY,
                CREATED_DT,
                MODIFIED_BY,
                MODIFIED_DT
            FROM TB_M_GIM_D GIMD
            WHERE 1=1
        """.trimIndent()
        )

        model.gimType?.takeIf { it.isNotBlank() }?.let {
            sql.append(" AND GIM_TYPE =:gimType ")
        }
        model.gimCd?.takeIf { it.isNotBlank() }?.let {
            sql.append(" AND UPPER(GIM_CD) = UPPER(:gimCd) ")
        }
        sql.append(" ORDER BY GIM_TYPE,GIM_CD ")
        log.info("sql >>>>> $sql")
        log.info("sql param >>>>> ${model.toJsonString()}")
        return namedParameterJdbcTemplate?.query(
            sql.toString(),
            BeanPropertySqlParameterSource(model),
            DataClassRowMapper.newInstance(GimDetail::class.java)
        )
    }

    fun findGimDetailByPrimaryKey(model: GimDetail): GimDetail? {
        val sql = """
            SELECT GIM_TYPE,
                GIM_CD,
                GIM_VALUE,
                FIELD1,
                FIELD2,
                FIELD3,
                ACTIVE_FLAG,
                (SELECT GIM_VALUE
                    FROM TB_M_GIM_D
                    WHERE GIM_TYPE = 'ACTIVE_FLAG' AND GIM_CD = GIMD.ACTIVE_FLAG)  AS DISPLAY_ACTIVE_FLAG,
                CREATED_BY,
                CREATED_DT
                MODIFIED_BY,
                MODIFIED_DT
            FROM TB_M_GIM_D GIMD
            WHERE GIM_TYPE = UPPER (:gimType) AND GIM_CD = UPPER (:gimCd)
        """.trimIndent()
        log.info("sql >>>>> $sql")
        log.info("sql param >>>>> ${model.toJsonString()}")
        return namedParameterJdbcTemplate?.queryForObject(
            sql,
            BeanPropertySqlParameterSource(model),
            DataClassRowMapper.newInstance(GimDetail::class.java)
        )
    }

    fun saveGimDetail(model: GimDetail): Int? {
        val sql = """
            INSERT INTO TB_M_GIM_D (GIM_TYPE,
                        GIM_CD,
                        GIM_VALUE,
                        FIELD1,
                        FIELD2,
                        FIELD3,
                        ACTIVE_FLAG,
                        CREATED_BY,
                        CREATED_DT,
                        MODIFIED_BY,
                        MODIFIED_DT)
            VALUES (UPPER (:gimType),
                UPPER (:gimCd),
                :gimValue,
                :field1,
                :field2,
                :field3,
                :activeFlag,
                :createdBy,
                SYSDATE,
                :modifiedBy,
                SYSDATE)
        """.trimIndent()

        log.info("sql >>>>> $sql")
        log.info("sql param >>>>> ${model.toJsonString()}")
        return namedParameterJdbcTemplate?.update(sql, BeanPropertySqlParameterSource(model))
    }

    fun updateActiveFlagOfGimHeaderByGimDetailActiveFlag(model: GimHeader): Int? {
        val sql = """
            UPDATE TB_M_GIM_H
            SET ACTIVE_FLAG = :activeFlag,
                MODIFIED_BY = :modifiedBy,
                MODIFIED_DT = SYSDATE
            WHERE GIM_TYPE = UPPER (:gimType)
        """.trimIndent()
        log.info("sql >>>>> $sql")
        log.info("sql param >>>>> ${model.toJsonString()}")
        return namedParameterJdbcTemplate?.update(sql, BeanPropertySqlParameterSource(model))
    }
    fun updateGimDetail(model: GimDetail): Int? {
        val sql = """
            UPDATE TB_M_GIM_D
            SET GIM_VALUE = :gimValue,
                FIELD1 = :field1,
                FIELD2 = :field2,
                FIELD3 = :field3,
                ACTIVE_FLAG = :activeFlag,
                MODIFIED_BY = :modifiedBy,
                MODIFIED_DT = SYSDATE
            WHERE     GIM_TYPE = UPPER ( :gimType)
                AND GIM_CD = UPPER ( :gimCd)
                AND MODIFIED_DT = :modifiedDt
        """.trimIndent()
        log.info("sql >>>>> $sql")
        log.info("sql param >>>>> ${model.toJsonString()}")
        return namedParameterJdbcTemplate?.update(sql, BeanPropertySqlParameterSource(model))
    }

    fun deleteGimDetailByKeys(model: GimDetail): Int?{
        val sql = """
            DELETE FROM TB_M_GIM_D
            WHERE     GIM_TYPE = UPPER (:gimType)
                    AND GIM_CD = UPPER (:gimCd)
                    AND MODIFIED_DT = :modifiedDt
        """.trimIndent()
        log.info("sql >>>>> $sql")
        log.info("sql param >>>>> ${model.toJsonString()}")
        return namedParameterJdbcTemplate?.update(sql, BeanPropertySqlParameterSource(model))
    }
}