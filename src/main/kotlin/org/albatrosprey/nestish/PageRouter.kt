package org.albatrosprey.nestish

import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono
import java.io.InputStreamReader

@RestController
class PageRouter(
    val handler: Handler,
) {
    @GetMapping(
        path = ["/echo/{number}"],
        produces = [MediaType.TEXT_HTML_VALUE],
    )
    suspend fun echoNumber(
        @PathVariable number: Int,
    ): String = "<body>number : ${PageRouter::class.java.classLoader.name}</body>"

    @GetMapping(
        path = ["/getpage/{number}"],
        produces = [MediaType.TEXT_HTML_VALUE],
    )
    suspend fun page(
        @PathVariable number: Int,
    ): String =
        this::class.java.classLoader.getResourceAsStream("index.html")?.let {
            InputStreamReader(
                it,
            ).readText()
        } ?: "file not found"

    @Bean
    fun myRouter(): RouterFunction<*> =
        router {
            GET("/router") {
                ok().render("index.html")
            }
        }
}

@Component
class Handler {
    fun echoNumber(request: ServerRequest): Mono<ServerResponse> =
        ServerResponse
            .ok()
            .contentType(MediaType.TEXT_HTML)
            .body(
                10,
                PageRouter::class.java,
            )
}
