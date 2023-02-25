package com.hawkeye.core

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

class JdbiFactory {

    /**
     * Return instance of JDBI with all plugins installed
     * @param dbName Name of the DB to connect
     * @param dropDbOnConnectionClose Set to true if for each connection close, in memory DB should be dropped
     */
    fun getInstance(dbName: String): Jdbi {
        return Jdbi.create(getConnectionStringByDatabaseName(dbName))
            .also {
                it.installPlugin(SqlObjectPlugin())
                it.installPlugin(KotlinPlugin())
                it.installPlugin(KotlinSqlObjectPlugin())
            }

    }

    private fun getConnectionStringByDatabaseName(dbName: String): String {
        return when(dbName) {
            "db-prod" -> "jdbc:sqlserver://<server-name>:<port>;database=$dbName;user=<username>;password=<password>;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"
            else -> ""
        }
    }
}