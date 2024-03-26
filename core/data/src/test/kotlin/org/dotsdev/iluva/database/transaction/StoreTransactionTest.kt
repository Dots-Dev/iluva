package org.dotsdev.iluva.database.transaction

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.util.UUID
import org.dotsdev.iluva.Store
import org.dotsdev.iluva.User
import org.dotsdev.iluva.database.table.StoreLocationTable
import org.dotsdev.iluva.database.table.StoreSettingTable
import org.dotsdev.iluva.database.table.StoreTable
import org.dotsdev.iluva.database.table.UserTable
import org.dotsdev.iluva.database.repository.StorePersistence
import org.dotsdev.iluva.database.repository.UserPersistence
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class StoreTransactionTest : FunSpec({
    beforeTest {
        transaction {
            SchemaUtils.create(UserTable, StoreTable, StoreSettingTable, StoreLocationTable)
        }
    }

    val storeTrx = StorePersistence()
    val userTrx = UserPersistence()
    var userId: String

    test("create new store should return store id") {
        userId = userTrx.create(User())
        val storeId = storeTrx.create(Store(name = "Store"), userId = userId)

        val store = storeTrx.findByStoreID(storeId)

        store shouldNotBe null
        store?.id shouldBe UUID.fromString(storeId)
    }
    test("find store by id should return store") {
        userId = userTrx.create(User())
        val storeId = storeTrx.create(Store(name = "Store"), userId = userId)

        val store = storeTrx.findByStoreID(storeId)

        store shouldNotBe null
        store?.id shouldBe UUID.fromString(storeId)
    }

    test("find store by id should return null when store not found") {
        userId = userTrx.create(User())
        storeTrx.create(Store(name = "Store"), userId = userId)

        val store = storeTrx.findByStoreID(UUID.randomUUID().toString())

        store shouldBe null
    }
})