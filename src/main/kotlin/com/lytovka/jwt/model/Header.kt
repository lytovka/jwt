package com.lytovka.jwt.model

import kotlinx.serialization.Serializable

@Serializable
data class Header(
    val alg: String,
    val typ: String,
    val kid: String? = null,
    val jku: String? = null,
)
