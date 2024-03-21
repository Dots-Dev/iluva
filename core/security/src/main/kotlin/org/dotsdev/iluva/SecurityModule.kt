package org.dotsdev.iluva

import config.Config
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.delegate
import org.kodein.di.instance

val securityModule = DI.Module("security") {
    bindSingleton { TokenProvider(instance<Config>().jwt) }
    bindSingleton { PasswordEncryptor() }
    delegate<IPasswordEncryptor>().to<PasswordEncryptor>()
}