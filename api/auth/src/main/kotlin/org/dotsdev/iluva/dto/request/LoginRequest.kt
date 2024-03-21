package org.dotsdev.iluva.dto.request

import kotlinx.serialization.Serializable
import so.kciter.thing.Rule
import so.kciter.thing.Thing
import so.kciter.thing.normalizer.trim
import so.kciter.thing.validator.email
import so.kciter.thing.validator.minLength
import so.kciter.thing.validator.notEmpty

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
) : Thing<LoginRequest> {
    override val rule: Rule<LoginRequest>
        get() = Rule {
            Normalization {
                LoginRequest::email { trim() }
                LoginRequest::password { trim() }
            }
            Validation {
                LoginRequest::email {
                    notEmpty() hint "Email is required"
                    email() hint "Invalid email format"
                }
                LoginRequest::password {
                    notEmpty() hint "Password is required"
                    minLength(8) hint "Password must be at least 8 characters"
                }
            }
        }
}
