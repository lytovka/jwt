package com.lytovka.jwt.model

import com.lytovka.jwt.utils.Base64
import com.lytovka.jwt.utils.SignatureBuilder
import java.security.PrivateKey

data class JwtToken(
    val header: String? = null,
    val payload: String? = null,
    val signature: String? = null,
) {
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
        signature = Base64.encode(SignatureBuilder.computeWithRSA(getUnprotectedToken(), key))
        hasSignature = true
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
