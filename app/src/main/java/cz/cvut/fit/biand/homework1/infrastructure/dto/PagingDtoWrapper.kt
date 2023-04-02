package cz.cvut.fit.biand.homework1.infrastructure.dto

import androidx.core.net.toUri
import cz.cvut.fit.biand.homework1.domain.model.Pagination
import cz.cvut.fit.biand.homework1.domain.model.PagingWrapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal open class PagingDtoWrapper<Type : Any> {
    private val results: List<Type> = listOf()
    private val info: PaginationDto = PaginationDto(0, 0, null, null)


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
    nextKey = next?.toUri()?.getQueryParameter("page"),
    prevKey = previous?.toUri()?.getQueryParameter("page"),
)
