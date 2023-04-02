package cz.cvut.fit.biand.homework1.domain.common

import cz.cvut.fit.biand.homework1.domain.model.PagingWrapper

interface BasePagingProvider<T> {
    suspend fun fetchData(page: String): Result<PagingWrapper<T>>
}
