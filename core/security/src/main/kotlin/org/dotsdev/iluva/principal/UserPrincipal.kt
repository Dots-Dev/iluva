package org.dotsdev.iluva.principal

import io.ktor.server.auth.Principal
import org.dotsdev.iluva.User

class UserPrincipal(val user: User): Principal