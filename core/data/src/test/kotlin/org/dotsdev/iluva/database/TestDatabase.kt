package org.dotsdev.iluva.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.dotsdev.iluva.database.table.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

object TestDatabase {
    val psql = PostgreSQLContainer<Nothing>(DockerImageName.parse("pgvector/pgvector:pg16")).apply {
        start()
    }

    init {
        val ds = HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = psql.jdbcUrl
                username = psql.username
                password = psql.password
                driverClassName = "org.postgresql.Driver"
                maximumPoolSize = 10
            }
        )
        Database.connect(ds)
        transaction {
            SchemaUtils.create(UserTable)
        }
    }
}