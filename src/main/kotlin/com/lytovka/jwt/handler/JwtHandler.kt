package com.lytovka.jwt.handler

import com.lytovka.jwt.service.JwtService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class JwtHandler(private val jwtService: JwtService) {
    suspend fun helloWorld(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().bodyValueAndAwait("Hello, World!")
    }

    suspend fun createJwt(request: ServerRequest): ServerResponse {
        val jwt = jwtService.createJwt()
        return ServerResponse.ok().bodyValueAndAwait(jwt)
    }
}
