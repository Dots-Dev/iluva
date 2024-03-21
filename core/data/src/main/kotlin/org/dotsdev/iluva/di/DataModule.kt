package org.dotsdev.iluva.di

import org.dotsdev.iluva.database.IluvaDatabase
import org.dotsdev.iluva.database.transaction.UserTransaction
import org.kodein.di.DI
import org.kodein.di.bindEagerSingleton
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val dataModule = DI.Module("database") {
    bindEagerSingleton { IluvaDatabase(instance()) }

    bindSingleton { UserTransaction() }
}