package org.dotsdev.iluva.database.table

import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object StoreTable : UUIDTable("stores") {
    val name: Column<String> = varchar("name", 50)
    val description: Column<String?> = text("description").nullable()
    val tagline: Column<String?> = text("tagline").nullable()
    val logo: Column<String?> = text("logo").nullable()
    val isDefault: Column<Boolean> = bool("is_default").default(false)
    val user = reference("user", UserTable, onDelete = ReferenceOption.CASCADE)
    val setting = reference("settings", StoreSettingTable)
    val createdAt: Column<Instant> = timestamp("created_at")
    val updatedAt: Column<Instant?> = timestamp("updated_at").nullable()
}

object StoreSettingTable : IntIdTable("store_settings") {
    val theme: Column<Int> = integer("theme").default(1)
    val domain: Column<String?> = varchar("domain", 100).nullable()
    val currency: Column<String> = varchar("currency", 10).default("IDR")
    val currencyCode: Column<String> = varchar("currency_code", 10).default("Rp")
    val isActive: Column<Boolean> = bool("is_active").default(true)
    val isStoreEnabled: Column<Boolean> = bool("is_store_enabled").default(true)
    val createdAt: Column<Instant> = timestamp("created_at")
    val updatedAt: Column<Instant?> = timestamp("updated_at").nullable()
}

object StoreLocationTable : IntIdTable("store_location") {
    val address: Column<String?> = text("address").nullable()
    val city: Column<String?> = text("city").nullable()
    val state: Column<String?> = text("state").nullable()
    val country: Column<String?> = text("country").nullable()
    val zipCode: Column<String?> = text("zip_code").nullable()
    val latitude: Column<Double?> = double("latitude").nullable()
    val longitude: Column<Double?> = double("longitude").nullable()
    val store = reference("store", StoreTable, onDelete = ReferenceOption.CASCADE)
    val createdAt: Column<Instant> = timestamp("created_at")
    val updatedAt: Column<Instant?> = timestamp("updated_at").nullable()
}