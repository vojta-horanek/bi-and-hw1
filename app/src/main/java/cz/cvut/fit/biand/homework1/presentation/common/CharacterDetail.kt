package cz.cvut.fit.biand.homework1.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.cvut.fit.biand.homework1.R
import cz.cvut.fit.biand.homework1.domain.model.Character
import cz.cvut.fit.biand.homework1.presentation.theme.Space

@Composable
fun CharacterDetail(
    character: Character,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Column {
            Column(
                modifier = Modifier
                    .padding(Space.Large),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Space.Large)
                ) {
                    AsyncImage(
                        model = character.image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(AvatarSize)
                            .clip(MaterialTheme.shapes.small)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.medium
                        ) {
                            Text(
                                text = stringResource(R.string.label_name),
                                style = MaterialTheme.typography.body1,
                            )
                        }
                        Text(
                            text = character.name.orUnknown(),
                            style = MaterialTheme.typography.h2,
                        )
                    }
                }
            }
            Divider()
            Column(
                modifier = Modifier
                    .padding(Space.Large),
                verticalArrangement = Arrangement.spacedBy(Space.Large)
            ) {
                Info(
                    label = stringResource(R.string.label_status),
                    value = character.status,
                )
                Info(
                    label = stringResource(R.string.label_species),
                    value = character.species,
                )
                Info(
                    label = stringResource(R.string.label_type),
                    value = character.type,
                )
                Info(
                    label = stringResource(R.string.label_gender),
                    value = character.gender,
                )
                Info(
                    label = stringResource(R.string.label_origin),
                    value = character.origin?.name,
                )
                Info(
                    label = stringResource(R.string.label_location),
                    value = character.location?.name,
                )
            }
        }
    }
}

@Composable
private fun Info(
    label: String,
    value: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.body2,
            )
        }
        Text(
            text = value.orUnknown(),
            style = MaterialTheme.typography.h3,
        )
    }
}

@Composable
fun String?.orUnknown() =
    this.takeUnless { it.isNullOrBlank() } ?: stringResource(R.string.label_nothing)

private val AvatarSize = 140.dp
