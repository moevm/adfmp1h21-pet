package abdulmanov.eduard.pets.domain.models

import java.lang.IllegalStateException

enum class Sex(val value: Int) {
    MALE(1),
    FEMALE(2);

    companion object{

        fun getSexFromValue(value: Int): Sex {
            return when(value){
                MALE.value -> MALE
                FEMALE.value -> FEMALE
                else -> throw IllegalStateException("Sex not found")
            }
        }
    }
}