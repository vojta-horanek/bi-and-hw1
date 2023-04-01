package cz.cvut.fit.biand.homework1.domain.common

import cz.cvut.fit.biand.homework1.domain.model.PagingWrapper

/**
 * An interface of a paging provider. Paging sources take this provider and use it to fetch their data.
 */
interface BasePagingProvider<T> {
    /**
     * This method returns data from the source based on the [paging] parameter.
     * When implemented, a use case can and should be used for the actual data fetching.
     * @param paging the parameters about which piece of data should be fetched.
     * @return a result type of an implemented paginated object.
     */
    suspend fun fetchData(page: String): Result<PagingWrapper<T>>
}
