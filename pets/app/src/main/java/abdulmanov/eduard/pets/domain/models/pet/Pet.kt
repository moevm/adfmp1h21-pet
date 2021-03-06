package abdulmanov.eduard.pets.domain.models.pet

data class Pet(
    val id: Int,
    val avatar: String,
    val name: String,
    val type: String,
    val sex: Sex,
    val birthDate: BirthDate,
    val isCurrent: Boolean
)