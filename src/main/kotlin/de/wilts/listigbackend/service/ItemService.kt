package de.wilts.listigbackend.service

import de.wilts.listigbackend.data.Item
import de.wilts.listigbackend.persistence.ItemRepository
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class ItemService {

    @Autowired private lateinit var itemRepository: ItemRepository
    @Autowired private lateinit var publisher: ApplicationEventPublisher

    fun save(item: Item): Mono<Item> {
        /** Spring cloud gcp does currently not support creation of documents with auto generated ids,
        so we need to generate our own. This code should be removed once auto generated ids are implemented.
         */
        if (item.messageId == "") {
            val uuid = UUID.randomUUID()
            val itemWithId = item.copy(messageId = uuid.toString())
            return doSave(itemWithId)
        }
        return doSave(item)
    }

    fun doSave(item: Item): Mono<Item> {
        return itemRepository
                .save(item)
                .doOnSuccess { publisher.publishEvent(ItemCreatedEvent(it)) }

    }

    fun findAll(): Flux<Item> {
        return itemRepository.findAll()
    }

    fun clear(): Mono<Void> {
        return itemRepository.deleteAll()
    }
}

class ItemCreatedEvent(source: Item) : ApplicationEvent(source)
