package abdulmanov.eduard.pets.data.sharedpreferences

import abdulmanov.eduard.pets.BuildConfig
import android.content.Context
import androidx.core.content.edit

class PetsSharedPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    var idCurrentPet: Int
        set(value) = sharedPreferences.edit { putInt(PREF_ID_CURRENT_PET, value) }
        get() = sharedPreferences.getInt(PREF_ID_CURRENT_PET, -1)

    companion object {
        private const val PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_pets"

        private const val PREF_ID_CURRENT_PET = "id_current_pet"
    }
}