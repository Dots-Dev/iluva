package org.dotsdev.iluva.exception

class UserNotFoundException(message: String): Exception(message)
class UserAlreadyRegisteredException(message: String): Exception(message)