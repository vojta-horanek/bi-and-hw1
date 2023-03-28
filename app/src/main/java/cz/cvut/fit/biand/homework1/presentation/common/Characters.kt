package cz.cvut.fit.biand.homework1.presentation.common

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
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            all = Space.Medium
        )
    ){
        items(characters) { character ->
            Character(
                name = character.name,
                status = character.status,
                avatarUri = character.avatarUri,
                isFavourite = character.isFavourite,
                onClick = {
                    onCharacterClick(character.id)
                },
            )
        }
    }
}