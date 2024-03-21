package org.dotsdev.iluva.dto.request

import extension.trim
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dotsdev.iluva.User
import so.kciter.thing.Rule
import so.kciter.thing.Thing
import so.kciter.thing.normalizer.trim
import so.kciter.thing.validator.email
import so.kciter.thing.validator.minLength
import so.kciter.thing.validator.notEmpty

@Serializable
data class RegisterRequest(
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String? = null,
    val email: String,
    val password: String,
    val avatar: String? = null
) : Thing<RegisterRequest> {
    override val rule: Rule<RegisterRequest>
        get() = Rule {
            Normalization {
                RegisterRequest::firstName { trim() }
                RegisterRequest::lastName { trim() }
                RegisterRequest::email { trim() }
                RegisterRequest::password { trim() }
                RegisterRequest::avatar{ trim() }
            }
            Validation {
                RegisterRequest::firstName { notEmpty() hint "First name is required" }
                RegisterRequest::email {
                    notEmpty() hint "Email is required"
                    email() hint "Invalid email format"
                }
                RegisterRequest::password {
                    notEmpty() hint "Password is required"
                    minLength(8) hint "Password must be at least 8 characters"
                }
            }
        }

    fun toDomain() = User(
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = password,
        avatar = avatar
    )
}
