package abdulmanov.eduard.pets.presentation._common

import abdulmanov.eduard.pets.R
import android.content.Context
import androidx.annotation.ArrayRes
import javax.inject.Inject

class StringProvider @Inject constructor(private val context: Context) {

    fun getArrayString(@ArrayRes resId: Int) = context.resources.getStringArray(resId)
}