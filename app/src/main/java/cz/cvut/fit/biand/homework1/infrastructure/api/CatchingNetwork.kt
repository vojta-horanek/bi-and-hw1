package cz.cvut.fit.biand.homework1.infrastructure.api

import io.ktor.client.plugins.*
import io.ktor.http.*

internal suspend fun <T : Any> catchingNetwork(
    defaultValue: T? = null,
    block: suspend () -> T,
) = try {
    Result.success(block())
} catch (e: ClientRequestException) {
    e.printStackTrace()
    if (defaultValue == null) {
        Result.failure(e)
    } else {
        when (e.response.status) {
            HttpStatusCode.NotFound -> Result.success(defaultValue)
            else -> Result.failure(e)
        }
    }
} catch (e: Exception) {
    e.printStackTrace()
    Result.failure(e)
}
