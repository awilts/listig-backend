package de.wilts.listigbackend.web

import de.wilts.listigbackend.service.ItemCreatedEvent
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class MyWebSocketHandler(eventPublisher: ItemCreatedEventPublisher) : WebSocketHandler {

    private val publish = Flux
            .create<ItemCreatedEvent>(eventPublisher)
            .share()

    override fun handle(session: WebSocketSession): Mono<Void> {
        val messageFlux = publish
                .map { evt -> evt.source.toString() }
                .map { str ->
                    session.textMessage(str)
                }
        return session.send(messageFlux)
    }
}