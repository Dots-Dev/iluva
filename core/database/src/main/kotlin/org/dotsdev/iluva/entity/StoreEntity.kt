package org.dotsdev.iluva.entity

import java.util.UUID
import org.dotsdev.iluva.table.StoreLocationTable
import org.dotsdev.iluva.table.StoreSettingTable
import org.dotsdev.iluva.table.StoreTable
import org.dotsdev.liuva.domain.Store
import org.dotsdev.liuva.domain.StoreLocation
import org.dotsdev.liuva.domain.StoreSetting
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class StoreEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<StoreEntity>(StoreTable)

    var name by StoreTable.name
    var description by StoreTable.description
    var tagline by StoreTable.tagline
    var logo by StoreTable.logo
    var createdAt by StoreTable.createdAt
    var updatedAt by StoreTable.updatedAt
    var user by UserEntity referencedOn StoreTable.user

    fun toDomain() = Store(
        id = id.value,
        name = name,
        description = description,
        tagline = tagline,
        logo = logo,
        setting = StoreSettingEntity.find { StoreSettingTable.store eq id }.first().toDomain(),
        location = StoreLocationEntity.find { StoreLocationTable.store eq id }.first().toDomain()
    )
}

class StoreSettingEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<StoreSettingEntity>(StoreSettingTable)

    var theme by StoreSettingTable.theme
    var domain by StoreSettingTable.domain
    var currency by StoreSettingTable.currency
    var currencyCode by StoreSettingTable.currencyCode
    var isActive by StoreSettingTable.isActive
    var isStoreEnabled by StoreSettingTable.isStoreEnabled
    var store by StoreEntity referencedOn StoreSettingTable.store
    var createdAt by StoreSettingTable.createdAt
    var updatedAt by StoreSettingTable.updatedAt

    fun toDomain() = StoreSetting(
        theme = theme,
        domain = domain,
        currency = currency,
        currencyCode = currencyCode,
        isActive = isActive,
        isStoreEnabled = isStoreEnabled
    )
}

class StoreLocationEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<StoreLocationEntity>(StoreLocationTable)

    var address by StoreLocationTable.address
    var city by StoreLocationTable.city
    var state by StoreLocationTable.state
    var country by StoreLocationTable.country
    var zipCode by StoreLocationTable.zipCode
    var latitude by StoreLocationTable.latitude
    var longitude by StoreLocationTable.longitude
    var store by StoreEntity referencedOn StoreLocationTable.store
    var createdAt by StoreLocationTable.createdAt
    var updatedAt by StoreLocationTable.updatedAt

    fun toDomain() = StoreLocation(
        address = address,
        city = city,
        state = state,
        country = country,
        zipCode = zipCode,
        latitude = latitude,
        longitude = longitude
    )
}