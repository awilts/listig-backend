package de.wilts.listigbackend.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import java.util.Collections.singletonMap
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Configuration
internal class WebSocketConfiguration {

    @Bean
    fun executor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Bean
    fun handlerMapping(wsh: WebSocketHandler?): HandlerMapping? {
        return object : SimpleUrlHandlerMapping() {
            init {
                urlMap = singletonMap("/ws/items", wsh)
                order = 10
            }
        }
    }

    @Bean
    fun webSocketHandlerAdapter(): WebSocketHandlerAdapter {
        return WebSocketHandlerAdapter()
    }

    @Bean
    fun webSocketHandler(eventPublisher: ItemCreatedEventPublisher): WebSocketHandler {
        return MyWebSocketHandler(eventPublisher)
    }
}
