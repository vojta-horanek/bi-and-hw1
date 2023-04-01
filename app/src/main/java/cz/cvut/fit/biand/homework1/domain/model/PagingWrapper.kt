package cz.cvut.fit.biand.homework1.domain.model


/**
 * This wrapper should be used for any entity when paging is considered.
 */
class PagingWrapper<T>(
    /**
     * The actual list of data fetched from a source.
     */
    val list: List<T>,
    /**
     * Info about this paginated object.
     */
    val pagination: Pagination,
)

data class Pagination(
    val count: Long,
    val pages: Long,
    val nextKey: String?,
    val prevKey: String?,
)

/**
 * Returns a pagination containing only elements matching the given predicate.
 */
fun <T> PagingWrapper<T>.filter(predicate: (T) -> Boolean) = PagingWrapper(
    list = list.filter(predicate),
    pagination = pagination,
)

/**
 * Returns a pagination containing the results of applying the given transform function to each element in the original pagination list.
 */
fun <T, R> PagingWrapper<T>.map(transform: (T) -> R) = PagingWrapper(
    list = list.map(transform),
    pagination = pagination,
)
