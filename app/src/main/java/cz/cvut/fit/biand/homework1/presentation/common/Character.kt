package cz.cvut.fit.biand.homework1.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.presentation.theme.AppTheme
import cz.cvut.fit.biand.homework1.presentation.theme.Space

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Character(
    name: String,
    status: String,
    avatarUri: String?,
    isFavourite: Boolean,
    insideCard: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val content = remember {
        movableContentOf {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(Space.Medium),
            ) {
                AsyncImage(
                    model = avatarUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.Top)
                        .size(AvatarSize)
                        .clip(MaterialTheme.shapes.small),
                )
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = Space.Large),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.h3,
                        )
                        if (isFavourite) {
                            Icon(
                                painter = painterResource(R.drawable.ic_favourite_selected),
                                contentDescription = null,
                                tint = MaterialTheme.colors.secondary,
                                modifier = Modifier
                                    .size(FavouriteSize),
                            )
                        }
                    }
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.medium
                    ) {
                        Text(
                            text = status,
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            }
        }
    }

    if (insideCard) {
        Card(
            onClick = onClick,
            elevation = 4.dp,
            modifier = modifier
                .fillMaxWidth(),
        ) {
            content()
        }
    } else {
        Box(
            modifier = modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable { onClick() }
                .fillMaxWidth(),
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun CharacterPreview() {
    AppTheme {
        Character(
            name = "Rick Sanchez",
            status = "Alive",
            avatarUri = "https://static.wikia.nocookie.net/rickandmorty/images/a/a6/Rick_Sanchez.png/revision/latest?cb=20160923150728",
            isFavourite = false,
            onClick = { },
            insideCard = true,
        )
    }
}

@Preview
@Composable
private fun CharacterPreviewFavorite() {
    AppTheme {
        Character(
            name = "Rick Sanchez",
            status = "Alive",
            avatarUri = "https://static.wikia.nocookie.net/rickandmorty/images/a/a6/Rick_Sanchez.png/revision/latest?cb=20160923150728",
            isFavourite = true,
            onClick = { },
            insideCard = false,
        )
    }
}

private val AvatarSize = 44.dp
private val FavouriteSize = 14.dp
