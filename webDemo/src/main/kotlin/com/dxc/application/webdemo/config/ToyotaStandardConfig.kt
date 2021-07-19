package com.dxc.application.webdemo.config

import org.apache.camel.spring.SpringCamelContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.Scope
import th.co.toyota.st3.api.download.CST30090ExcelGenerator
import th.co.toyota.st3.api.download.CST30091Downloader
import th.co.toyota.st3.api.report.CST30170JasperReportConnector
import th.co.toyota.st3.api.util.CST30000BatchManager
import th.co.toyota.st3.api.util.CST32010DocNoGenerator
import java.math.BigInteger

@Configuration
@PropertySource("classpath:demo-application.properties")
class ToyotaStandardConfig(
    private val applicationContext: ApplicationContext
) {
    @Bean
    fun camelContext() = SpringCamelContext(applicationContext)

    @Value("\${jr.template.folder}")
    private lateinit var jrTemplateFolder: String

    @Value("\${jr.destination.folder}")
    private lateinit var jrDestinationFolder: String

    @Value("\${jr.virtualizer.folder}")
    private lateinit var jrVirtualizerFolder: String

    @Value("\${jr.virtualMaxSize}")
    private lateinit var jrvirtualMaxSize: BigInteger

    @Bean
    @Scope("prototype")
    fun jasperReportConnector() = CST30170JasperReportConnector()
        .apply {
            setTemplateFolder(jrTemplateFolder)
            setDestinationFolder(jrDestinationFolder)
            setVirtualizerFolder(jrVirtualizerFolder)
            setVirtualMaxSize(jrvirtualMaxSize.toInt())
        }

    @Value("\${rowsperpage}")
    private lateinit var rowsperpage: BigInteger

    @Value("\${default.download.folder}")
    private lateinit var defaultDownloadFolder: String

    @Value("\${report.dateformat}")
    private lateinit var reportDateformat: String

    @Bean
    @Scope("prototype")
    fun excelGenerator() = CST30090ExcelGenerator()
        .apply {
            setMaxRowsPerPage(rowsperpage.toInt())
            setDateTimeFormat(reportDateformat)
            setSharedFolder(defaultDownloadFolder)
        }

    @Bean
    @Scope("prototype")
    fun downloader() = CST30091Downloader()
        .apply {
            setDefaultPath(defaultDownloadFolder)
        }

    @Bean
    @Scope("prototype")
    fun docNoGenerator() = CST32010DocNoGenerator()

    @Bean
    @Scope("prototype")
    fun batchManager() = CST30000BatchManager()
}