package org.dotsdev.liuva.response

import io.ktor.http.HttpStatusCode

sealed class HttpResponse<T : IResponse> {
    abstract val body: T
    abstract val code: HttpStatusCode

    data class OK<T : IResponse>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.OK
    }

    data class NotFound<T : IResponse>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.NotFound
    }

    data class BadRequest<T : IResponse>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.BadRequest
    }

    data class Unauthorized<T : IResponse>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.Unauthorized
    }

    companion object {
        fun <T : IResponse> OK(response: T) = OK(body = response)
        fun <T : IResponse> NotFound(response: T) = NotFound(body = response)
        fun <T : IResponse> BadRequest(response: T) = BadRequest(body = response)
        fun <T : IResponse> Unauthorized(response: T) = Unauthorized(body = response)
    }
}

fun generateHttpResponse(response: IResponse): HttpResponse<IResponse> = when (response.status) {
    State.SUCCESS -> HttpResponse.OK(response)
    State.NOT_FOUND -> HttpResponse.NotFound(response)
    State.FAILED -> HttpResponse.BadRequest(response)
    State.UNAUTHORIZED -> HttpResponse.Unauthorized(response)
}