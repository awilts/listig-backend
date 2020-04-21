package de.wilts.listigbackend.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse


@Configuration
class ItemEndpointConfiguration {

    @Bean
    fun routes(handler: ItemHandler): RouterFunction<ServerResponse> {
        return route(GET("/backend/items"), HandlerFunction { handler.all(it) })
                .andRoute(POST("/backend/items"), HandlerFunction { handler.create(it) })
                .andRoute(POST("/backend/items/clear"), HandlerFunction { handler.clear(it) })
    }
}