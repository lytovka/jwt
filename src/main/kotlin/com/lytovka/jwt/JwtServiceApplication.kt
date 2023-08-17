package com.lytovka.jwt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JwtServiceApplication

fun main(args: Array<String>) {
    runApplication<JwtServiceApplication>(*args)
}
