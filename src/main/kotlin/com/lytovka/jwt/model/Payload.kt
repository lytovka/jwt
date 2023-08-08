package com.lytovka.jwt.model

import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val sub: String,
    val role: String
)