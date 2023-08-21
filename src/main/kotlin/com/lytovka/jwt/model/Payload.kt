package com.lytovka.jwt.model

import com.fasterxml.jackson.annotation.JsonInclude
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@JsonInclude(JsonInclude.Include.NON_NULL)
@Serializable
open class Payload(
    // Registered claims
    val iss: String? = null, // issuer
    val sub: String? = null, // subject
    val aud: String? = null, // audience
    val exp: Long? = null, // expiration time
    val nbf: Long? = null, // not before
    val iat: Long? = null, // issued at
    val jti: String? = null, // JWT ID

    // Private claims
    val role: String? = null,
) {
    fun serialized(): String {
        return Json.encodeToString(Payload.serializer(), this)
    }

    fun getExpiresIn(): Long {
        val e = exp ?: throw IllegalStateException("Expiration time is not set")
        val i = iat ?: throw IllegalStateException("Issued at time is not set")

        return e - i
    }
}
