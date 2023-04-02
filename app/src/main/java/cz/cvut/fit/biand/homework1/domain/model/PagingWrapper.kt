package cz.cvut.fit.biand.homework1.domain.model


class PagingWrapper<T>(
    val list: List<T>,
    val pagination: Pagination,
)

data class Pagination(
    val count: Long,
    val pages: Long,
    val nextKey: String?,
    val prevKey: String?,
)

fun <T, R> PagingWrapper<T>.map(transform: (T) -> R) = PagingWrapper(
    list = list.map(transform),
    pagination = pagination,
)
