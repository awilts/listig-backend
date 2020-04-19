package de.wilts.listigbackend.persistence

import de.wilts.listigbackend.data.Item
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository

interface ItemRepository : FirestoreReactiveRepository<Item> {
}