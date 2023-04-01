package cz.cvut.fit.biand.homework1.di

import cz.cvut.fit.biand.homework1.data.repository.CharactersRepositoryImpl
import cz.cvut.fit.biand.homework1.data.source.CharactersLocalSource
import cz.cvut.fit.biand.homework1.data.source.CharactersRemoteSource
import cz.cvut.fit.biand.homework1.domain.provider.CharactersPagingProvider
import cz.cvut.fit.biand.homework1.domain.repository.CharactersRepository
import cz.cvut.fit.biand.homework1.domain.usecase.GetCharacterByIdUseCase
import cz.cvut.fit.biand.homework1.domain.usecase.GetCharactersUseCase
import cz.cvut.fit.biand.homework1.domain.usecase.GetFavouriteCharactersUseCase
import cz.cvut.fit.biand.homework1.domain.usecase.ModifyCharacterFavouriteUseCase
import cz.cvut.fit.biand.homework1.infrastructure.api.CharactersApi
import cz.cvut.fit.biand.homework1.infrastructure.api.CharactersApiImpl
import cz.cvut.fit.biand.homework1.infrastructure.api.KtorClient
import cz.cvut.fit.biand.homework1.infrastructure.source.CharactersLocalSourceImpl
import cz.cvut.fit.biand.homework1.infrastructure.source.CharactersRemoteSourceImpl
import cz.cvut.fit.biand.homework1.presentation.common.CharactersPagingSource
import cz.cvut.fit.biand.homework1.presentation.detail.DetailViewModel
import cz.cvut.fit.biand.homework1.presentation.favourite.FavouritesViewModel
import cz.cvut.fit.biand.homework1.presentation.overview.OverviewViewModel
import cz.cvut.fit.biand.homework1.presentation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(KtorClient::create)
    singleOf(::CharactersApiImpl) bind CharactersApi::class
}

val infrastructureModule = module {
    singleOf(::CharactersRemoteSourceImpl) bind CharactersRemoteSource::class
    singleOf(::CharactersLocalSourceImpl) bind CharactersLocalSource::class
}

val dataModule = module {
    singleOf(::CharactersRepositoryImpl) bind CharactersRepository::class
}

val domainModule = module {
    factoryOf(::GetCharactersUseCase)
    factoryOf(::GetCharacterByIdUseCase)
    factoryOf(::ModifyCharacterFavouriteUseCase)
    factoryOf(::GetFavouriteCharactersUseCase)
    factory { (name: String?) -> CharactersPagingProvider(name, get()) }
}

val presentationModule = module {
    factory { (name: String?) -> CharactersPagingSource(get { parametersOf(name) }) }

    viewModelOf(::OverviewViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::FavouritesViewModel)
}