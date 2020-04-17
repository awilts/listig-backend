package de.wilts.listigbackend

import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.DatastoreOptions
import com.google.cloud.datastore.Entity
import com.google.cloud.datastore.Key
import org.springframework.web.bind.annotation.*

@RequestMapping("/backend")
@RestController
class MessageController {

    private final val testItem = Item("messageId", "myid", "groupId", "some deep message")
    val items = mutableListOf<Item>(testItem)

    @PostMapping("/items")
    fun postItems(@RequestBody item: Item) {
        println(item)
        items.add(item)

        val kind = "itemKind"
        val name = "itemKeyName"
        val taskKey: Key = datastore.newKeyFactory().setKind(kind).newKey(name)

        val task: Entity = Entity.newBuilder(taskKey)
                .set("messageId", item.messageId)
                .set("myid", item.userId)
                .set("groupId", item.groupId)
                .set("text", item.text)
                .build()

        datastore.put(task)
        System.out.printf("Saved %s: %s%n", task.key.name, task.getString("description"))

        val retrieved = datastore[taskKey]
        System.out.printf("Retrieved %s: %s%n", taskKey.name, retrieved.getString("description"))
    }

    @GetMapping("/items")
    fun getItems(): Collection<Item> {
        println(items)
        return items
    }

    val datastore: Datastore = DatastoreOptions.getDefaultInstance().service

}
