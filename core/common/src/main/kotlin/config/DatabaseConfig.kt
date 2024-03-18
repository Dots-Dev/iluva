package config

data class DatabaseConfig(
    val host: String,
    val maxPollSize: Int,
    val password: String,
    val name: String,
    val schema: String,
    val type: DatabaseType,
    val username: String,
    val port: String,
    val isReadOnly: Boolean,
) {
    val driver = when (type) {
        DatabaseType.POSTGRES -> "org.postgresql.Driver"
        DatabaseType.MYSQL -> "com.mysql.cj.jdbc.Driver"
        DatabaseType.MARIADB -> "org.mariadb.jdbc.Driver"
    }

    val jdbc = when (type) {
        DatabaseType.POSTGRES -> "jdbc:postgresql:"
        DatabaseType.MYSQL -> "jdbc:mysql:"
        DatabaseType.MARIADB -> "jdbc:mariadb:"
    }
}

enum class DatabaseType {
    POSTGRES,
    MYSQL,
    MARIADB
}