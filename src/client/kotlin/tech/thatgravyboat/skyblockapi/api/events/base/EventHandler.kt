package tech.thatgravyboat.skyblockapi.api.events.base

internal class EventHandler<T : SkyblockEvent> private constructor(
    val name: String,
    private val listeners: List<EventListeners.Listener>,
    private val canReceiveCancelled: Boolean,
) {

    constructor(event: Class<T>, listeners: List<EventListeners.Listener>) : this(
        (event.name.split(".").lastOrNull() ?: event.name).replace("$", "."),
        listeners.sortedBy { it.options.priority }.toList(),
        listeners.any { it.options.receiveCancelled }
    )

    fun post(event: T, onError: ((Throwable) -> Unit)? = null): Boolean {
        if (this.listeners.isEmpty()) return false

        for (listener in listeners) {
            if (!shouldInvoke(event, listener)) continue
            try {
                listener.invoker.accept(event)
            } catch (throwable: Throwable) {
                onError ?: throw throwable
                onError.invoke(throwable)
            }
            if (event.isCancelled && !canReceiveCancelled) break
        }
        return event.isCancelled
    }

    private fun shouldInvoke(event: SkyblockEvent, listener: EventListeners.Listener) =
        !(event.isCancelled && !listener.options.receiveCancelled)
}