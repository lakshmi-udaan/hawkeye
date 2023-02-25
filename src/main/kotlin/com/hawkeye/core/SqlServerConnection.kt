package com.hawkeye.core

import com.udaan.snorql.framework.metric.Connection
import com.udaan.snorql.framework.models.HistoricalDatabaseResult
import javax.inject.Inject

class SqlServerConnection @Inject constructor(private val jdbi3Factory: JdbiFactory) : Connection {
    override fun <T> run(databaseName:String, query: String, mapClass: Class<T>,params: Map<String, *>): List<T> {
        return if(params.isNotEmpty()){
            jdbi3Factory.getInstance(databaseName).withHandle<List<T>, Exception> { handle ->
                handle.createQuery(query).bindMap(params).mapTo(mapClass).toList()
            }
        } else{
            jdbi3Factory.getInstance(databaseName).withHandle<List<T>, Exception> { handle ->
                handle.createQuery(query).mapTo(mapClass).toList() as List<T>
            }
        }
    }

    override fun storeData(storageBucketId: String, columns: List<String>, rows: List<List<Any>>) {
        TODO("Not yet implemented")
    }

    override fun getHistoricalData(
        storageBucketId: String,
        metricId: String,
        databaseName: String,
        columns: List<String>,
        paginationParams: Map<String, *>,
        params: Map<String, *>
    ): HistoricalDatabaseResult {
        TODO("Not yet implemented")
    }
}