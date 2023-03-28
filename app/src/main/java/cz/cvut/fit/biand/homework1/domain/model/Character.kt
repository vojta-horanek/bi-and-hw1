package cz.cvut.fit.biand.homework1.domain.model

data class Character(
    val id: Long,
    val name: String,
    val status: String,
    val avatarUri: String,
    val isFavourite: Boolean,
)
