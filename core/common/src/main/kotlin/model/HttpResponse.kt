package model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class HttpResponse<T>(
    val data: T?,
    val message: String? = null,
    val code: Int? = null
) {
    companion object {
        fun <T> ok(data: T) = HttpResponse(data)

        fun badRequest(message: String) = HttpResponse(null, message, HttpStatusCode.BadRequest.value)

        fun unauthorized(message: String) = HttpResponse(null, message, HttpStatusCode.Unauthorized.value)

        fun notFound(message: String) = HttpResponse(null, message, HttpStatusCode.NotFound.value)

        fun error(message: String, code: HttpStatusCode) = HttpResponse(null, message, code.value)
    }
}