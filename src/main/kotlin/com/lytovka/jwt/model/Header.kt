package com.lytovka.jwt.model

import kotlinx.serialization.Serializable

@Serializable
data class Header(
    val alg: String,
    val typ: String? = null,
    val kid: String? = null,
)
