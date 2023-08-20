package com.lytovka.jwt.service

import com.lytovka.jwt.utils.KeyGenerator
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.security.KeyFactory
import java.security.KeyPair
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

@Service
class KeyService {
    private val keyPath = "keystore.p12"

    init {
        if (!File(keyPath).exists()) {
            generateAndStoreKeyPair()
        }
    }

    private final fun generateAndStoreKeyPair() {
        val keyPair = KeyGenerator().generatePair()

        val privateKeyBytes = keyPair.privateKey.encoded
        val publicKeyBytes = keyPair.publicKey.encoded

        val buffer = ByteBuffer.allocate(4 + privateKeyBytes.size + 4 + publicKeyBytes.size)
        buffer.putInt(privateKeyBytes.size)
        buffer.put(privateKeyBytes)
        buffer.putInt(publicKeyBytes.size)
        buffer.put(publicKeyBytes)

        FileOutputStream(keyPath).use { it.write(buffer.array()) }
    }

    fun loadKeyPair(): KeyPair {
        val buffer = ByteBuffer.wrap(FileInputStream(keyPath).readBytes())
        val privateKeySize = buffer.int
        val privateKeyBytes = ByteArray(privateKeySize)
        buffer.get(privateKeyBytes)

        val publicKeySize = buffer.int
        val publicKeyBytes = ByteArray(publicKeySize)
        buffer.get(publicKeyBytes)

        val keyFactory = KeyFactory.getInstance("RSA")
        val privateKey = keyFactory.generatePrivate(PKCS8EncodedKeySpec(privateKeyBytes))
        val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(publicKeyBytes))

        return KeyPair(publicKey, privateKey)
    }
}
