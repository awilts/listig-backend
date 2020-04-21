package de.wilts.listigbackend.web

import de.wilts.listigbackend.service.ItemCreatedEvent
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class MyWebSocketHandler(eventPublisher: ItemCreatedEventPublisher) : WebSocketHandler {

    private val mapper = jacksonObjectMapper()
    private val publish = Flux
            .create<ItemCreatedEvent>(eventPublisher)
            .share()

    override fun handle(session: WebSocketSession): Mono<Void> {
        val messageFlux = publish
                .map { mapper.writeValueAsString(it.source) }
                .map { session.textMessage(it) }
        return session.send(messageFlux)
    }
}