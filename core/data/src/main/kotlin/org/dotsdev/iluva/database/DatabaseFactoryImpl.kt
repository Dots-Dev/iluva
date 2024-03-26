package org.dotsdev.iluva.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import config.Config
import javax.sql.DataSource
import org.dotsdev.iluva.database.table.StoreLocationTable
import org.dotsdev.iluva.database.table.StoreSettingTable
import org.dotsdev.iluva.database.table.StoreTable
import org.dotsdev.iluva.database.table.UserTable
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.FlywayException
import org.jetbrains.annotations.TestOnly
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.slf4j.LoggerFactory

interface DatabaseFactory {
    fun connect()
    fun close()

    suspend fun <T> execute(block: suspend () -> T) : T
}

class DatabaseFactoryImpl : DatabaseFactory, KoinComponent {
    private val config: Config by inject()
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun connect() {
        Database.connect(dataSource())
        runFlyway(dataSource())
        SchemaDefinitions.createSchema()
    }

    private fun dataSource(): HikariDataSource {
        val config = HikariConfig()
        with(this.config.database) {
            config.driverClassName = driver
            config.jdbcUrl = "$jdbc//$host:$port/$name"
            config.username = username
            config.password = password
            config.schema = schema
            config.maximumPoolSize = maxPollSize
            config.isReadOnly = isReadOnly
            config.isAutoCommit = false
            config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }
        config.validate()
        return HikariDataSource(config)
    }

    private fun initDatabase() {
        val tables = arrayOf(UserTable, StoreTable, StoreSettingTable, StoreLocationTable)
        Database.connect(dataSource())
        runFlyway(dataSource())
        transaction {
            SchemaUtils.createMissingTablesAndColumns(*tables)
        }
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
            log.error(e.message, e)
        }
        log.info("Flyway migration finished")
    }

    @TestOnly
    override fun close() {}
    override suspend fun <T> execute(block: suspend () -> T): T = newSuspendedTransaction { block() }
    object SchemaDefinitions {
        fun createSchema() {
            val tables = arrayOf(UserTable, StoreTable, StoreSettingTable, StoreLocationTable)
            transaction {
                SchemaUtils.create(*tables)
            }
        }
    }

}