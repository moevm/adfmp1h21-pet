package abdulmanov.eduard.pets.domain.models.interview

data class Interview(
    val id: Int,
    val rating: Int,
    val date: String,
    val petId: Int
)