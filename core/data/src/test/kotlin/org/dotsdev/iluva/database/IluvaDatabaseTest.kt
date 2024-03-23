package org.dotsdev.iluva.database

import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException
import config.Config
import config.DatabaseConfig
import config.DatabaseType
import config.JWTConfig
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import org.dotsdev.iluva.database.IluvaDatabase

class IluvaDatabaseTest : BehaviorSpec({
    val baseConfig = Config(
        env = "test",
        database = DatabaseConfig(
            host = "localhost",
            maxPollSize = 10,
            password = "postgres",
            name = "postgres",
            schema = "iluva",
            type = DatabaseType.POSTGRES,
            username = "postgres",
            port = "5432",
            isReadOnly = false
        ),
        jwt = JWTConfig(
            secret = "secret_key",
            issuer = "issuer",
            realm = "realm",
            audience = "audience"
        )
    )
    context("should connect to database") {
        given("valid config object") {
            `when`("initiate database") {
                val db = IluvaDatabase(baseConfig).testInitDatabase()
                then("database should not be null") {
                    db shouldNotBe null
                }
            }
        }
    }
    context("should throws an exception") {
        given("invalid database credentials") {
            `when`("initiate database") {
                val db = IluvaDatabase(
                    baseConfig.copy(
                        database = baseConfig.database.copy(username = "invalid", password = "invalid")
                    )
                )
                then("throws an exception") {
                    shouldThrow<PoolInitializationException> { db.testInitDatabase() }
                }
            }
        }
    }
})