package org.dotsdev.iluva

import org.koin.dsl.module


val authModule = module {
    single { AuthRepository() }
    single { AuthService(get(), get(), get()) }
}