package com.lytovka.jwt.utils

import java.util.Base64

class B64 {

    companion object {
        /**
         * Base64 encoded string without padding.
         */

        fun encode(input: ByteArray): String {
            return Base64.getEncoder().withoutPadding().encodeToString(input)
        }

        /**
         * Base64 URL encoded string without padding.
         */
        fun urlEncode(input: ByteArray): String {
            return Base64.getUrlEncoder().withoutPadding().encodeToString(input)
        }

        /**
         * Decodes Base64 encoded string.
         */
        fun decode(input: String): ByteArray {
            return Base64.getDecoder().decode(input)
        }

        /**
         * Decodes Base64 URL encoded string.
         */
        fun urlDecode(input: String): ByteArray {
            return Base64.getUrlDecoder().decode(input)
        }
    }
}
