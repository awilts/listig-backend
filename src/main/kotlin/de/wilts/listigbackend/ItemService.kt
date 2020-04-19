package de.wilts.listigbackend

import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class ItemService(@Autowired var itemRepository: ItemRepository) {
    fun save(item: Item): Mono<Item> {

        /** spring cloud gcp does currently not support creation of documents with auto generated ids,
            so we need to generate our own. This code should be removed once auto generated ids are implemented.
        */
        if (item.messageId == "") {
            val uuid = UUID.randomUUID()
            val itemWithId = item.copy(messageId = uuid.toString())
            return itemRepository.save(itemWithId)
        }
        return itemRepository.save(item)
    }

    fun findAll(): Publisher<Item> {
        return itemRepository.findAll()
    }
}