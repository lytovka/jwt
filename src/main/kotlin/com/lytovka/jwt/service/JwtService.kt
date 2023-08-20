package com.lytovka.jwt.service

import com.lytovka.jwt.configuration.RequestContext
import com.lytovka.jwt.dto.JwtTokenResponse
import com.lytovka.jwt.model.Header
import com.lytovka.jwt.model.Payload
import com.lytovka.jwt.utils.Base64
import com.lytovka.jwt.utils.SignatureBuilder
import com.nimbusds.jose.JWSAlgorithm
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Service
import java.util.Date
import java.util.UUID

@Service
class JwtService(private val requestContext: RequestContext, private val keyService: KeyService) {
    private val kid = "my-key-id"

    fun createJwt(): JwtTokenResponse {
        val header = buildHeader()
        val payload = buildPayload()
        val serializedHeader = Json.encodeToString(Header.serializer(), header)
        val serializedPayload = Json.encodeToString(Payload.serializer(), payload)
        val encodedHeaderRsa = Base64.urlEncode(serializedHeader.encodeToByteArray())
        val encodedPayloadRsa = Base64.urlEncode(serializedPayload.encodeToByteArray())

        val unprotectedJwtRsa = "$encodedHeaderRsa.$encodedPayloadRsa"
        val keyPair = keyService.loadKeyPair()
        val encodedSignature = Base64.urlEncode(SignatureBuilder.computeWithRSA(unprotectedJwtRsa, keyPair.private))

        return JwtTokenResponse(accessToken = "$unprotectedJwtRsa.$encodedSignature", expiresIn = 3600)
    }

    private fun buildHeader(): Header {
        return Header(
            alg = JWSAlgorithm.RS256.name,
            kid = kid,
            typ = "JWT",
//            jku = "${getIssuerUrl()}.well-known/jwks.json",
        )
    }

    private fun buildPayload(): Payload {
        val issuer = getIssuerUrl()
        val date = Date().time
        return Payload(
            sub = "1234567890",
            role = "user",
            iss = issuer,
            aud = "https://example.com",
            exp = (date + 60 * 60 * 1000) / 1000,
            iat = date / 1000,
            jti = UUID.randomUUID().toString(),
        )
    }

    private fun getIssuerUrl(): String {
        val headers = requestContext.httpHeaders ?: throw IllegalStateException("Http headers are not set")
        val scheme = headers.getFirst("X-Forwarded-Proto") ?: "http"
        val host = headers.getFirst("Host") ?: "localhost"
        return "$scheme://$host/"
    }
}
