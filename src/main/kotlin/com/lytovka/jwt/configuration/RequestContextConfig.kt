package com.lytovka.jwt.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.http.HttpHeaders

open class RequestContext {
    var httpHeaders: HttpHeaders? = null
}

@Configuration
class RequestContextConfig {
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun requestContext(): RequestContext {
        return RequestContext()
    }
}
