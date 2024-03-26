package di

import config.Config
import org.dotsdev.iluva.PasswordEncryptor
import org.dotsdev.iluva.PasswordEncryptorImpl
import org.dotsdev.iluva.TokenProvider
import org.koin.dsl.module

val commonModule = module {
    single { TokenProvider(get<Config>().jwt) }
    single<PasswordEncryptor> { PasswordEncryptorImpl() }
}