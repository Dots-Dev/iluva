package org.dotsdev.iluva.di

import org.dotsdev.iluva.database.DatabaseFactory
import org.dotsdev.iluva.database.DatabaseFactoryImpl
import org.dotsdev.iluva.database.repository.StorePersistence
import org.dotsdev.iluva.database.repository.UserPersistence
import org.koin.dsl.module

val dataModule = module {
    single<DatabaseFactory> { DatabaseFactoryImpl() }
    single { UserPersistence() }
    single { StorePersistence() }
}