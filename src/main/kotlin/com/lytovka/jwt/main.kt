package com.lytovka.jwt

import com.lytovka.jwt.model.Header
import com.lytovka.jwt.model.Payload
import com.lytovka.jwt.utils.Encoding
import kotlinx.serialization.json.Json

fun main() {
  val header = Header(
    alg = "HS256"
  )
  val payload = Payload(
    sub = "1234567890",
    role = "admin"
  )

  val serializedHeader = Json.encodeToString(Header.serializer(), header)
  val serializedPayload = Json.encodeToString(Payload.serializer(), payload)

  val result = Encoding.encode(serializedHeader, serializedPayload)

  println(result)
}
