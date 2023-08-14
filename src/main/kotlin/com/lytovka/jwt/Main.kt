package com.lytovka.jwt

import com.lytovka.jwt.model.Header
import com.lytovka.jwt.model.Payload
import com.lytovka.jwt.utils.Base64
import com.lytovka.jwt.utils.KeyGenerator
import com.lytovka.jwt.utils.SignatureBuilder
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSVerifier
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jwt.SignedJWT
import kotlinx.serialization.json.Json
import java.lang.IllegalArgumentException
import java.security.interfaces.RSAPublicKey

fun generateJWKStringFromPublicKey(kid: String, rsaPublicKey: RSAPublicKey): JWKSet {
    val jwk = RSAKey.Builder(rsaPublicKey)
        .keyUse(KeyUse.SIGNATURE)
        .keyID(kid)
        .algorithm(JWSAlgorithm.RS256)
        .build()

    return JWKSet(jwk)
}

fun verifyJWT(token: String, jwkSet: JWKSet): Boolean {
    val jwt = SignedJWT.parse(token)
    val jwk = jwkSet.getKeyByKeyId(jwt.header.keyID) ?: throw IllegalArgumentException("JWK not found")
    val verifier: JWSVerifier = RSASSAVerifier(jwk.toRSAKey().toRSAPublicKey())
    return jwt.verify(verifier)
}

fun main() {
    val kid = "my-key-id"
    val headerRsa = Header(
        alg = JWSAlgorithm.RS256.name,
        kid = kid,
    )
    val payloadRsa = Payload(
        sub = "1234567890",
        role = "user",
    )
    val serializedHeaderRsa = Json.encodeToString(Header.serializer(), headerRsa)
    val serializedPayloadRsa = Json.encodeToString(Payload.serializer(), payloadRsa)
    val encodedHeaderRsa = Base64.urlEncode(serializedHeaderRsa.encodeToByteArray())
    val encodedPayloadRsa = Base64.urlEncode(serializedPayloadRsa.encodeToByteArray())
    val unprotectedJwtRsa = "$encodedHeaderRsa.$encodedPayloadRsa"
    val keyPair = KeyGenerator().generatePair()
    val signatureRsa = Base64.urlEncode(SignatureBuilder.computeWithRSA(unprotectedJwtRsa, keyPair.privateKey))
    val jwtRsa = "$unprotectedJwtRsa.$signatureRsa"
    println(jwtRsa)
    val jwks = generateJWKStringFromPublicKey(kid, keyPair.publicKey)
    println(jwks.getKeyByKeyId(kid))
    val isVerified = verifyJWT(jwtRsa, jwks)
    println(isVerified)
}
