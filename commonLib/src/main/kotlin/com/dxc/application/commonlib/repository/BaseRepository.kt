package com.dxc.application.commonlib.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport
import java.util.*
import javax.sql.DataSource

abstract class BaseRepository(
) : NamedParameterJdbcDaoSupport() {
    @Autowired
    @Qualifier("appDataSource")
    fun setJdbcDaoSupportDataSource(dataSource: DataSource) {
        Locale.setDefault(Locale.US)
        super.setDataSource(dataSource)
    }
}