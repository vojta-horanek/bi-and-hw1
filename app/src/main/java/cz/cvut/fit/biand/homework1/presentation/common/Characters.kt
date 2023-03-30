package cz.cvut.fit.biand.homework1.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.presentation.theme.Space
import kotlinx.collections.immutable.ImmutableList

@Composable
fun Characters(
    characters: ImmutableList<Character>,
    onCharacterClick: (id: Long) -> Unit,
    insideCard: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(
        all = Space.Medium
    )
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(Space.Medium)
    ) {
        items(characters) { character ->
            Character(
                name = character.name.orEmpty(),
                status = character.status.orEmpty(),
                avatarUri = character.image,
                insideCard = insideCard,
                isFavourite = character.isFavourite,
                onClick = {
                    onCharacterClick(character.id)
                },
            )
        }
    }
}