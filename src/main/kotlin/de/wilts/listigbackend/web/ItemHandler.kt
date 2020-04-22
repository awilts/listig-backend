//package de.wilts.listigbackend.web
//
//import de.wilts.listigbackend.data.Item
//import de.wilts.listigbackend.service.ItemService
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.MediaType.APPLICATION_JSON
//import org.springframework.stereotype.Component
//import org.springframework.web.reactive.function.server.ServerRequest
//import org.springframework.web.reactive.function.server.ServerResponse
//import reactor.core.publisher.Mono
//import java.net.URI
//
//@Component
//class ItemHandler {
//
//    @Autowired
//    private lateinit var itemService: ItemService
//
//    fun all(request: ServerRequest): Mono<ServerResponse> {
//        return ServerResponse
//                .ok()
//                .contentType(APPLICATION_JSON)
//                .body(itemService.findAll(), Item::class.java)
//    }
//
//    fun create(request: ServerRequest): Mono<ServerResponse> {
//        return request
//                .bodyToMono(Item::class.java)
//                .flatMap { itemService.save(it) }
//                .flatMap {
//                    ServerResponse
//                            .created(URI.create("/items/${it.messageId}"))
//                            .contentType(APPLICATION_JSON)
//                            .build()
//                }
//    }
//
//    fun clear(request: ServerRequest): Mono<ServerResponse>  {
//        return ServerResponse
//                .noContent()
//                .build( itemService.clear())
//    }
//}