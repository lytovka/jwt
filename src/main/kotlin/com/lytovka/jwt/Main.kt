package com.lytovka.jwt

import com.lytovka.jwt.model.Header
import com.lytovka.jwt.model.Payload
import com.lytovka.jwt.utils.Base64
import com.lytovka.jwt.utils.HashBasedMessageAuthenticationCode
import kotlinx.serialization.json.Json

// Validate JWT
fun validateJWT(jwt: String, secret: String): Boolean {
    val parts = jwt.split(".")
    if (parts.size != 3) return false

    val header = parts[0]
    val payload = parts[1]
    val providedSignature = Base64.urlDecode(parts[2])

    val data = "$header.$payload"
    val computedSignature = HashBasedMessageAuthenticationCode.computeHmac(data, secret)

    return computedSignature.contentEquals(providedSignature)
}

fun main() {
    val header = Header(
        alg = "HS256",
    )
    val payload = Payload(
        sub = "1234567890",
        role = "admin",
    )

    val serializedHeader = Json.encodeToString(Header.serializer(), header).encodeToByteArray()
    val serializedPayload = Json.encodeToString(Payload.serializer(), payload).encodeToByteArray()

    val encodedHeader = Base64.urlEncode(serializedHeader)
    val encodedPayload = Base64.urlEncode(serializedPayload)
    val unprotectedJwt = "$encodedHeader.$encodedPayload"

    val secret = "my-secret-key"
    val signature = Base64.urlEncode(HashBasedMessageAuthenticationCode.computeHmac(unprotectedJwt, secret))
    val jwt = "$unprotectedJwt.$signature"
    println(jwt)
    val isValid = validateJWT(jwt, "$secret+")
    println(isValid)
}
