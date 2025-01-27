package tech.thatgravyboat.skyblockapi.utils.extentions

/**
 * Splits a list into chunks based on a predicate.
 * The predicate will be true when a new chunk should be created, will include the element in the new chunk.
 */
internal fun <T> List<T>.chunked(predicate: (T) -> Boolean): MutableList<MutableList<T>> {
    val chunks = mutableListOf<MutableList<T>>()
    for (element in this) {
        val currentChunk = chunks.lastOrNull()
        if (currentChunk == null || predicate(element)) {
            chunks.add(mutableListOf(element))
        } else {
            currentChunk.add(element)
        }
    }
    return chunks
}

internal inline fun <T> List<T>.peek(crossinline block: (T) -> Unit): List<T> {
    for (element in this) {
        block(element)
    }
    return this
}

internal fun <K> MutableMap<K, Int>.addOrPut(key: K, number: Int): Int = merge(key, number, Int::plus)!!

internal fun <K> MutableMap<K, Double>.addOrPut(key: K, number: Double): Double = merge(key, number, Double::plus)!!

internal fun <K> MutableMap<K, Float>.addOrPut(key: K, number: Float): Float = merge(key, number, Float::plus)!!

internal fun <K> MutableMap<K, Long>.addOrPut(key: K, number: Long): Long = merge(key, number, Long::plus)!!
