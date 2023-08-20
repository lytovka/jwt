package com.lytovka.jwt.service

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.stereotype.Service
import java.security.interfaces.RSAPublicKey

@Service
class JwkService(private val keyService: KeyService) {
    private val kid = "my-key-id"

    fun getJwks(): String {
        val keyPair = keyService.loadKeyPair()
        val jwk = RSAKey.Builder(keyPair.public as RSAPublicKey)
            .keyUse(KeyUse.SIGNATURE)
            .keyID(kid)
            .algorithm(JWSAlgorithm.RS256)
            .build()

        return JWKSet(jwk).toString()
    }
}
