package org.dotsdev.iluva

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val authModule = DI.Module("auth-api") {
    bindSingleton { AuthRepository(instance()) }
    bindSingleton { AuthController(instance(), instance(), instance()) }
}