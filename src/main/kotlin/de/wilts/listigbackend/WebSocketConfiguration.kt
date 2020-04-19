package de.wilts.listigbackend

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import reactor.core.publisher.Flux
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
    fun handlerMapping(wsh: WebSocketHandler?): HandlerMapping {
        return SimpleUrlHandlerMapping(singletonMap("/ws/items", wsh), 10)
    }

    @Bean
    fun webSocketHandlerAdapter(): WebSocketHandlerAdapter {
        return WebSocketHandlerAdapter()
    }

    @Bean
    fun webSocketHandler(objectMapper: ObjectMapper, eventPublisher: ItemCreatedEventPublisher): WebSocketHandler {

        val publish = Flux
                .create<ItemCreatedEvent>(eventPublisher)
                .share()

        publish.log().subscribe()

        return WebSocketHandler { session: WebSocketSession ->
            val messageFlux = publish
                    .map { evt: ItemCreatedEvent ->
                        try {
                            return@map objectMapper.writeValueAsString(evt.source)
                        } catch (e: JsonProcessingException) {
                            throw RuntimeException(e)
                        }
                    }
                    .map { str: String ->
                        println("sending $str")
                        session.textMessage(str)
                    }
            session.send(messageFlux)
        }
    }
}

