package org.dotsdev.iluva.router

import io.ktor.resources.Resource

@Resource("/auth")
class Auth {
    @Resource("login")
    class Login

    @Resource("register")
    class Register
}