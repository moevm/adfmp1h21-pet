package abdulmanov.eduard.pets.presentation._common.extensions


inline fun String.ifNotEmpty(action: (String) -> String): String {
    return if(isNotEmpty()) action(this) else this
}