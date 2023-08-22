package com.lytovka.jwt.utils

import java.security.PrivateKey
import java.security.Signature
import java.security.interfaces.RSAPublicKey

class Signature {
    object Builder {
        fun computeWithRSA(jwtBody: String, privateKey: PrivateKey): ByteArray {
            println("sign jwtBody: $jwtBody")
            val signatureInstance = Signature.getInstance(ALG)
            signatureInstance.initSign(privateKey)
            signatureInstance.update(jwtBody.encodeToByteArray())
            return signatureInstance.sign()
        }
    }

    object Verifier {
        fun verifyRSA(
            jwtBody: String,
            jwtSignature: ByteArray,
            publicKey: RSAPublicKey,
        ): Boolean {
            val signatureInstance = Signature.getInstance(ALG)
            signatureInstance.initVerify(publicKey)
            signatureInstance.update(jwtBody.encodeToByteArray())
            return signatureInstance.verify(jwtSignature)
        }
    }

    companion object {
        const val ALG = "SHA256withRSA"
    }
}
