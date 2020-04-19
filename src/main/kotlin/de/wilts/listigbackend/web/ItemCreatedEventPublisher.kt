package de.wilts.listigbackend.web

import de.wilts.listigbackend.service.ItemCreatedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils
import reactor.core.publisher.FluxSink
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.function.Consumer

@Component
class ItemCreatedEventPublisher(private val executor: Executor) : ApplicationListener<ItemCreatedEvent>, Consumer<FluxSink<ItemCreatedEvent?>> {

    private val queue: BlockingQueue<ItemCreatedEvent> = LinkedBlockingQueue()
    override fun onApplicationEvent(event: ItemCreatedEvent) {
        queue.offer(event)
    }

    override fun accept(sink: FluxSink<ItemCreatedEvent?>) {
        executor.execute {
            while (true) try {
                val event: ItemCreatedEvent = queue.take()
                sink.next(event)
            } catch (e: InterruptedException) {
                ReflectionUtils.rethrowRuntimeException(e)
            }
        }
    }
}