package com.hawkeye

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.hawkeye.configuration.AppConfig
import com.hawkeye.core.SqlServerConnection
import com.hawkeye.resources.SnorqlMetricsResource
import com.udaan.snorql.extensions.SQLCommonMetrics
import com.udaan.snorql.framework.metric.SqlMetricManager
import io.dropwizard.Application
import io.dropwizard.setup.Environment

class HawkeyeApplication: Application<AppConfig>() {
    companion object {
        @JvmStatic fun main(args: Array<String>) = HawkeyeApplication().run(*args)
    }

    override fun run(appConfig: AppConfig, environment: Environment) {
        environment.jersey().register(SnorqlMetricsResource::class.java)
        registerSQLMetrics()
    }

    private fun registerSQLMetrics() {
        val sqlServerConnectionInstance =
            Guice.createInjector(SqlClientModule()).getInstance(SqlServerConnection::class.java)
        SqlMetricManager.setConnection(sqlServerConnectionInstance)
        SQLCommonMetrics.initialize()
    }
}

class SqlClientModule : AbstractModule()