package com.lytovka.jwt.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Header(
    val alg: String,
    val typ: String,
    val kid: String? = null,
    val jku: String? = null,
) {
    fun serialized(): String {
        return Json.encodeToString(serializer(), this)
    }
}
