package org.dotsdev.iluva.dao

import org.dotsdev.liuva.Store

interface StoreDao {
    suspend fun storeStore(name: String, description: String?, tagline: String?, logo: String?): Store
    suspend fun findByID(id: String): Store?
}