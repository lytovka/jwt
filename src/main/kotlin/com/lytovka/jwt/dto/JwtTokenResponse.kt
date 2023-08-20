package com.lytovka.jwt.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class JwtTokenResponse(
    val accessToken: String,
    val expiresIn: Long,
    val tokenType: String = "Bearer",
)
