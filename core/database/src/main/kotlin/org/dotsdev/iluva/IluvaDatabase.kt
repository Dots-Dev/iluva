package org.dotsdev.iluva

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import config.Config
import config.DatabaseConfig
import javax.sql.DataSource
import org.dotsdev.iluva.table.StoreLocationTable
import org.dotsdev.iluva.table.StoreSettingTable
import org.dotsdev.iluva.table.StoreTable
import org.dotsdev.iluva.table.UserTable
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.FlywayException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

class IluvaDatabase(private val config: Config) {
    private val log = LoggerFactory.getLogger(this::class.java)

    init {
        initSQLDatabase()
    }

    private fun initSQLDatabase() {
        val tables = arrayOf(UserTable, StoreTable, StoreSettingTable, StoreLocationTable)

        val hikari = hikari(config.database)
        Database.connect(hikari)
        runFlyway(hikari)
        transaction {
            SchemaUtils.createMissingTablesAndColumns(*tables)
        }
    }

    private fun hikari(databaseConfig: DatabaseConfig): DataSource {
        val config = HikariConfig()
        with(databaseConfig) {
            config.driverClassName = driver
            config.jdbcUrl = "$jdbc//$host:$port/$name"
            config.username = username
            config.password = password
            config.schema = schema
            config.maximumPoolSize = maxPollSize
            config.isReadOnly = isReadOnly
        }
        config.validate()
        return HikariDataSource(config)
    }

    private fun runFlyway(dataSource: DataSource) {
        val flyway = Flyway.configure()
            .locations("classpath:db/migration/main")
            .dataSource(dataSource)
            .load()

        try {
            flyway.info()
            flyway.baseline()
            flyway.migrate()
        } catch (e: FlywayException) {
            throw e
        }
        log.info("Flyway migration finished")
    }

}