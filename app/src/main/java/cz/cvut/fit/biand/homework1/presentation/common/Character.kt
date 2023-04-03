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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.material.placeholder
import cz.cvut.fit.biand.homework1.R
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
            elevation = CardElevation,
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

@Composable
fun CharacterSkeleton(
    insideCard: Boolean,
    modifier: Modifier = Modifier,
) {
    val content = remember {
        movableContentOf {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(Space.Medium),
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Top)
                        .size(AvatarSize)
                        .clip(MaterialTheme.shapes.small)
                        .placeholder(
                            visible = true,
                        ),
                )
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = Space.Large),
                ) {
                    Text(
                        style = MaterialTheme.typography.h3,
                        text = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .placeholder(
                                visible = true
                            )
                    )
                    Spacer(modifier = Modifier.height(Space.Small))
                    Text(
                        text = "",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .placeholder(
                                visible = true
                            ),
                    )
                }
            }
        }
    }

    if (insideCard) {
        Card(
            elevation = CardElevation,
            modifier = modifier
                .fillMaxWidth(),
        ) {
            content()
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            content()
        }
    }
}

private val AvatarSize = 44.dp
private val FavouriteSize = 14.dp
private val CardElevation = 4.dp
