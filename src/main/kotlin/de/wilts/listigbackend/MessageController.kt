package de.wilts.listigbackend

import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.ServiceOptions
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
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
        val projectId: String = ServiceOptions.getDefaultProjectId()
        println("id:$projectId")

        val credentials = ServiceAccountCredentials.getApplicationDefault()
        val options: FirebaseOptions = FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build()
        FirebaseApp.initializeApp(options)
        val db: Firestore = FirestoreClient.getFirestore()
        db.collection("items").add(item);
    }

    @GetMapping("/items")
    fun getItems(): Collection<Item> {
        println(items)
        return items
    }
}
