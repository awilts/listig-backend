package de.wilts.listigbackend

import de.wilts.listigbackend.data.Item
import de.wilts.listigbackend.service.ItemService
import de.wilts.listigbackend.web.ItemEndpointConfiguration
import de.wilts.listigbackend.web.ItemHandler
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux.just


@WebFluxTest
@Import(ItemHandler::class)
internal class ItemControllerTest {

    @Autowired
    private lateinit var client: WebTestClient
    @Autowired
    private lateinit var itemHandler: ItemHandler

    @MockBean
    private lateinit var itemService: ItemService

    @Test
    fun getItems() {
        val router = ItemEndpointConfiguration().routes(itemHandler)
        val client = WebTestClient.bindToRouterFunction(router).build()
        val testItem = Item("mId", "uId", "gId", "someText")
        doReturn(just(testItem)).`when`(itemService).findAll()

        client.get()
                .uri("/backend/items")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].messageId").isEqualTo("mId")
                .jsonPath("$.[0].userId").isEqualTo("uId")
                .jsonPath("$.[0].groupId").isEqualTo("gId")
                .jsonPath("$.[0].text").isEqualTo("someText")
    }
}