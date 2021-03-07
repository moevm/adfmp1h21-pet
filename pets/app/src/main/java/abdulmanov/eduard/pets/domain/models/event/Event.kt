package abdulmanov.eduard.pets.domain.models.event

data class Event(
    val id: Int,
    val name: String,
    val repeatMode: RepeatMode,
    val date: String,
    val isNotification: Boolean,
    val time: String,
    val petId: Int
)