package cz.cvut.fit.biand.homework1.domain.model

data class Character(
    val id: Long,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: CharacterOrigin?,
    val location: CharacterLocation?,
    val image: String?,
    val episode: List<String>,
    val url: String?,
    val created: String?,
    val isFavourite: Boolean,
)