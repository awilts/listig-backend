package de.wilts.listigbackend.web

import de.wilts.listigbackend.data.Item
import de.wilts.listigbackend.service.ItemService
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/backend")
class ItemController {

    @Autowired
    private lateinit var itemService: ItemService

    @GetMapping("/items")
    fun findAll(): Flow<Item> {
        return itemService.findAll()
    }

    @PostMapping("/items")
    suspend fun save(@RequestBody item: Item): Item {
        println(item)
        return itemService.save(item)
    }

    @PostMapping("/items/clear")
    fun clear() : Mono<Void> {
        return itemService.clear().then()
    }
}