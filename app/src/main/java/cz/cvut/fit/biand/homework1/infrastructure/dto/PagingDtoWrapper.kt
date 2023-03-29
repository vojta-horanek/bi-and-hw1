package cz.cvut.fit.biand.homework1.infrastructure.dto

import cz.cvut.fit.biand.homework1.domain.model.Pagination
import cz.cvut.fit.biand.homework1.domain.model.PagingWrapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal open class PagingDtoWrapper<Type : Any> {
    val results: List<Type> = listOf()
    val info: PaginationDto = PaginationDto(0, 0, null, null)

    /**
     * @return a domain paging wrapper. The data is mapped using the [typeMapper] lambda.
     */
    fun <DomainType> toDomain(typeMapper: (Type) -> DomainType): PagingWrapper<DomainType> =
        PagingWrapper(results.map(typeMapper), info.toDomain())
}

@Serializable
internal data class PaginationDto(
    @SerialName("count")
    val count: Long,
    val pages: Long,
    @SerialName("next")
    val next: String?,
    @SerialName("prev")
    val previous: String?,
)

internal fun PaginationDto.toDomain() = Pagination(
    count = count,
    pages = pages,
    next = next,
    previous = previous
)
