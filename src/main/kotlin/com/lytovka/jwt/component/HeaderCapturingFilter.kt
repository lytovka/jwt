package com.lytovka.jwt.component

import com.lytovka.jwt.configuration.RequestContext
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class HeaderCapturingFilter(private val requestContext: RequestContext) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        requestContext.httpHeaders = exchange.request.headers
        return chain.filter(exchange)
    }
}
