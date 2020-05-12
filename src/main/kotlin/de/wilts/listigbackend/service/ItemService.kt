package de.wilts.listigbackend.service

import de.wilts.listigbackend.data.Item
import de.wilts.listigbackend.persistence.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
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

    @Autowired
    private lateinit var itemRepository: ItemRepository
    @Autowired
    private lateinit var publisher: ApplicationEventPublisher

    suspend fun save(item: Item): Item {
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

    suspend fun doSave(item: Item): Item {
        return itemRepository
                .save(item)
                .doOnSuccess { publisher.publishEvent(ItemCreatedEvent(it)) }
                .awaitFirst()
    }

    fun findAll(): Flow<Item> {
        return itemRepository.findAll().asFlow()
    }

    fun clear(): Mono<Void> {
        return itemRepository.deleteAll()
    }
}

class ItemCreatedEvent(source: Item) : ApplicationEvent(source)
