package com.lytovka.jwt.utils

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class HashBasedMessageAuthenticationCode {
    companion object {
        fun computeHmac(input: String, secret: String): ByteArray {
            val alg = "HmacSHA256"
            val secretKeySpec = SecretKeySpec(secret.encodeToByteArray(), alg)
            val mac = Mac.getInstance(alg)
            mac.init(secretKeySpec)
            return mac.doFinal(input.encodeToByteArray())
        }
    }
}
