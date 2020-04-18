package de.wilts.listigbackend

import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RequestMapping("/backend")
@RestController
class MessageController(@Autowired var itemRepository: ItemRepository) {

    @PostMapping("/items")
    fun postItems(@RequestBody item: Item): Mono<Item> {
        return itemRepository.save(item)
    }

    @GetMapping("/items")
    fun getItems(): Publisher<Item> {
        return itemRepository.findAll()
    }
}
