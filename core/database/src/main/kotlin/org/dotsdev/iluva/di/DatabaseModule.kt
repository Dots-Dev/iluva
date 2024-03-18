package org.dotsdev.iluva.di

import org.dotsdev.iluva.IluvaDatabase
import org.dotsdev.iluva.transaction.IUserTransaction
import org.dotsdev.iluva.transaction.UserTransaction
import org.kodein.di.*

val databaseModule = DI.Module("database") {
    bindEagerSingleton { IluvaDatabase(instance()) }

    bindSingleton { UserTransaction() }
}