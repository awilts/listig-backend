package de.wilts.listigbackend

import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
class MessageController(@Autowired var itemService: ItemService) {

    @PostMapping("/backend/items")
    fun postItems(@RequestBody item: Item): Mono<Item> {
        return itemService.save(item)
    }

    @GetMapping("/ws/items")
    fun getItems(): Publisher<Item> {
        return itemService.findAll()
    }
}
