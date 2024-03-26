package org.dotsdev.iluva

import org.dotsdev.iluva.database.repository.UserPersistence
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthRepository : KoinComponent {

    private val persistence: UserPersistence by inject()


}