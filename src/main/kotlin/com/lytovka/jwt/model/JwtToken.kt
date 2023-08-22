package com.lytovka.jwt.model

import com.lytovka.jwt.utils.Base64
import com.lytovka.jwt.utils.Signature
import java.security.PrivateKey

data class JwtToken(
    var header: String? = null,
    var payload: String? = null,
    var signature: String? = null,
) {
    fun getUnprotectedToken(): String {
        val h = header ?: throw IllegalStateException("Header is not set")
        val p = payload ?: throw IllegalStateException("Payload is not set")
        return "$h.$p"
    }

    override fun toString(): String {
        val h = header ?: throw IllegalStateException("Header is not set")
        val p = payload ?: throw IllegalStateException("Payload is not set")
        val unprotectedToken = "$h.$p"
        val s = signature ?: return unprotectedToken
        return "$unprotectedToken.$s"
    }
}

class JwtTokenBuilder {
    private var header: String? = null
    private var payload: String? = null
    private var signature: String? = null
    private var hasSignature = false

    fun setHeader(header: Header): JwtTokenBuilder {
        this.header = Base64.urlEncode(header.serialized().encodeToByteArray())
        return this
    }

    fun setPayload(payload: Payload): JwtTokenBuilder {
        this.payload = Base64.urlEncode(payload.serialized().encodeToByteArray())
        return this
    }

    fun signWith(key: PrivateKey): JwtTokenBuilder {
        if (hasSignature) {
            throw IllegalStateException("Signature already set")
        }
        signature = Base64.urlEncode(Signature.Builder.computeWithRSA(getUnprotectedToken(), key))
        hasSignature = true
        return this
    }

    fun parse(headerValue: String): JwtTokenBuilder {
        if (!headerValue.startsWith("Bearer ")) {
            throw IllegalArgumentException("Invalid authorization header format")
        }
        val headerParts = headerValue.split("Bearer ")
        if (headerParts.size < 2) {
            throw IllegalArgumentException("Invalid authorization header format")
        }

        val token = headerParts[1]
        val parts = token.split(".")

        if (parts.size != 2 && parts.size != 3) {
            throw IllegalArgumentException("Invalid number of token parts. Expected 2 or 3, but found ${parts.size}")
        }

        header = parts[0]
        payload = parts[1]

        if (parts.size == 3) {
            signature = parts[2]
        }
        return this
    }

    fun build(): JwtToken {
        return JwtToken(header, payload, signature)
    }

    private fun getUnprotectedToken(): String {
        val h = header ?: throw IllegalStateException("Header is not set")
        val p = payload ?: throw IllegalStateException("Payload is not set")
        return "$h.$p"
    }
}
