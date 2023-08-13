package com.lytovka.jwt.utils

import java.security.MessageDigest

class SecureHashAlgorithm {
    companion object {
        fun sha256(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }
        fun sha512(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-512").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }
}