package com.lytovka.jwt.router

import com.lytovka.jwt.handler.JwtHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class JwtRouter {

    @Bean
    fun jwtServiceRouter(jwtHandler: JwtHandler) = coRouter {
        "/jwt".nest {
//            POST("/create", jwtHandler::createToken)
            GET("/create", jwtHandler::createToken)
        }
    }
}