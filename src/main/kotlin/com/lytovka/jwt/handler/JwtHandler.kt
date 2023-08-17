package com.lytovka.jwt.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class JwtHandler {
    suspend fun createToken(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().bodyValueAndAwait("Hello, World!")
    }
}