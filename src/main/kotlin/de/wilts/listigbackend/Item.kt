package de.wilts.listigbackend

import com.google.cloud.firestore.annotation.DocumentId
import org.springframework.cloud.gcp.data.firestore.Document

@Document(collectionName = "items")
data class Item(@DocumentId val messageId: String?,
                val userId: String?,
                val groupId: String?,
                val text: String="")