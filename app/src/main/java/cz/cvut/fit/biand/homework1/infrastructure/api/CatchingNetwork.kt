package cz.cvut.fit.biand.homework1.infrastructure.api

internal suspend fun <T: Any> catchingNetwork(
    block: suspend () -> T,
) = try {
    Result.success(block())
} catch (e: Exception) {
    // TODO, improve
    Result.failure(e)
}