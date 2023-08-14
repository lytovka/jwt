package com.lytovka.jwt.utils

import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

data class RSAKeyPair(val publicKey: RSAPublicKey, val privateKey: RSAPrivateKey)

class KeyGenerator(private val keySize: Int = 2048) {
    fun generatePair(): RSAKeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(keySize)
        val keyPair = keyPairGenerator.genKeyPair()
        return RSAKeyPair(
            keyPair.public as RSAPublicKey,
            keyPair.private as RSAPrivateKey,
        )
    }
}
