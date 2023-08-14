package com.lytovka.jwt.utils

import java.security.PrivateKey
import java.security.Signature
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class SignatureBuilder {
    companion object {
        fun computeWithHMAC(input: String, secret: String): ByteArray {
            val alg = "HmacSHA256"
            val secretKeySpec = SecretKeySpec(secret.encodeToByteArray(), alg)
            val mac = Mac.getInstance(alg)
            mac.init(secretKeySpec)
            return mac.doFinal(input.encodeToByteArray())
        }

        fun computeWithRSA(input: String, privateKey: PrivateKey): ByteArray {
            val alg = "SHA256withRSA"
            val signature = Signature.getInstance(alg)
            signature.initSign(privateKey)
            signature.update(input.encodeToByteArray())
            return signature.sign()
        }
    }
}
