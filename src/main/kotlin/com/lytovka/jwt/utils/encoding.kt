package com.lytovka.jwt.utils

import java.util.Base64

object Encoding {

  /**
   * Base64 URL encoded string without padding.
   */
  private fun b46(input: String): String {
    val bytes = input.encodeToByteArray()
    return Base64.getEncoder().encodeToString(bytes).replace("=", "")
      .replace("/", "_")
      .replace("+", "-")
  }

  /**
   * Encodes header and payload to JWT string. The resulted JTW is **not** signed.
   */
  fun encode(h: String, p: String): String {
    val header = b46(h)
    val payload = b46(p)
    return "$header.$payload."
  }

  /**
   * Decodes JWT string to header and payload.
   */
  fun decode(jwt: String): Pair<String, String> {
    val parts = jwt.split(".")
    val header = String(Base64.getDecoder().decode(parts[0]))
    val payload = String(Base64.getDecoder().decode(parts[1]))
    return Pair(header, payload)
  }
}
