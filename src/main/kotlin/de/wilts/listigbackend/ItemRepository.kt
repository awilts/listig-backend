package de.wilts.listigbackend

import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository

interface ItemRepository : FirestoreReactiveRepository<Item> {
}