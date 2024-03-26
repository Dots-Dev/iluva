package org.dotsdev.iluva

import java.util.UUID

data class Store(
    val id: UUID? = null,
    val name: String,
    val description: String? = null,
    val tagline: String? = null,
    val logo: String? = null,
    val isDefault: Boolean = false,
    val setting: StoreSetting? = null,
    val location: StoreLocation? = null
)

typealias Stores = List<Store>

data class StoreSetting(
    val theme: Int,
    val domain: String? = null,
    val currency: String = "IDR",
    val currencyCode: String = "Rp",
    val isActive: Boolean = true,
    val isStoreEnabled: Boolean = true
)

data class StoreLocation(
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val zipCode: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)