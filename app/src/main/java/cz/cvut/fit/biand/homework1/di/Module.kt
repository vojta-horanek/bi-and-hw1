package cz.cvut.fit.biand.homework1.di

import cz.cvut.fit.biand.homework1.data.repository.CharactersRepositoryImpl
import cz.cvut.fit.biand.homework1.data.source.CharactersRemoteSource
import cz.cvut.fit.biand.homework1.domain.repository.CharactersRepository
import cz.cvut.fit.biand.homework1.infrastructure.api.CharactersApi
import cz.cvut.fit.biand.homework1.infrastructure.api.CharactersApiImpl
import cz.cvut.fit.biand.homework1.infrastructure.api.KtorClient
import cz.cvut.fit.biand.homework1.infrastructure.source.CharactersRemoteSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(KtorClient::create)
    singleOf(::CharactersApiImpl) bind CharactersApi::class
}

val dataModule = module {
    singleOf(::CharactersRemoteSourceImpl) bind CharactersRemoteSource::class
    singleOf(::CharactersRepositoryImpl) bind CharactersRepository::class
}

val appModule = module {

}