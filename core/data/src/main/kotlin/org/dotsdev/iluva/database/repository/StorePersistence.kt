package org.dotsdev.iluva.database.repository

import java.util.UUID
import kotlinx.datetime.Clock
import org.dotsdev.iluva.Store
import org.dotsdev.iluva.database.dbQuery
import org.dotsdev.iluva.database.entity.StoreEntity
import org.dotsdev.iluva.database.entity.StoreLocationEntity
import org.dotsdev.iluva.database.entity.StoreSettingEntity
import org.dotsdev.iluva.database.entity.UserEntity

class StorePersistence {
    suspend fun create(params: Store, userId: String): String = dbQuery {
        val storeSetting = StoreSettingEntity.new {
            createdAt = Clock.System.now()
        }
        val newStore = StoreEntity.new {
            name = params.name
            description = params.description
            tagline = params.tagline
            logo = params.logo
            isDefault = params.isDefault
            this.user = UserEntity.findById(UUID.fromString(userId))!!
            createdAt = Clock.System.now()
            settings = storeSetting
        }

        StoreLocationEntity.new {
            store = StoreEntity.findById(newStore.id)!!
            createdAt = Clock.System.now()
        }

        return@dbQuery newStore
    }.id.value.toString()

    suspend fun findByStoreID(id: String): Store? = dbQuery {
        StoreEntity.findById(UUID.fromString(id))?.toDomain()
    }
}