package cz.cvut.fit.biand.homework1.presentation.common

import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.domain.provider.CharactersPagingProvider
import cz.cvut.fit.biand.homework1.presentation.paging.BasePagingSource

class CharactersPagingSource(
    provider: CharactersPagingProvider,
) : BasePagingSource<Character>(
    provider = provider,
)