package abdulmanov.eduard.pets.presentation.interview.model

import abdulmanov.eduard.pets.domain.models.interview.Interview

data class InterviewPresentationModel(
    val id: Int = 0,
    val rating: Int = 0,
    val date: String,
    val petId: Int = -1
){

    fun isNew() = id == 0

    companion object {

        fun fromDomain(interview: Interview): InterviewPresentationModel {
            return InterviewPresentationModel(
                id = interview.id,
                rating = interview.rating,
                date = interview.date,
                petId = interview.petId
            )
        }

        fun toDomain(interview: InterviewPresentationModel): Interview {
            return Interview(
                id = interview.id,
                rating = interview.rating,
                date = interview.date,
                petId = interview.petId
            )
        }
    }
}