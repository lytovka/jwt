package com.lytovka.jwt.handler

import com.lytovka.jwt.service.JwkService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class JwkHandler(private val jwkService: JwkService) {
    suspend fun getJwks(request: ServerRequest): ServerResponse {
        val jwks = jwkService.getJwks()
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(jwks)
    }
}
