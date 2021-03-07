package abdulmanov.eduard.pets.presentation.options.dialogs.change_pet.models

import abdulmanov.eduard.pets.R
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class AddPresentationModel(
    @DrawableRes val image: Int = R.drawable.ic_plus,
    @StringRes val title: Int = R.string.change_pet_add
)