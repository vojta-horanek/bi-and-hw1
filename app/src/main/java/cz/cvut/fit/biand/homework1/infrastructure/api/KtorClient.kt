package cz.cvut.fit.biand.homework1.infrastructure.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
object KtorClient {
    fun create() = HttpClient(CIO) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                explicitNulls = true
                encodeDefaults = true
            })
        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "rickandmortyapi.com"
                path("api/")
            }
            contentType(ContentType.Application.Json)
        }
    }
}