package abdulmanov.eduard.pets.presentation._common.extensions

import android.widget.EditText
import android.widget.RadioGroup
import androidx.core.widget.addTextChangedListener
import kotlin.reflect.KMutableProperty

fun EditText.bind(property: KMutableProperty<String>) = addTextChangedListener { property.setter.call(it.toString()) }

fun RadioGroup.bind(property: KMutableProperty<*>, map:(Int) -> Any) = setOnCheckedChangeListener { _, checkedId ->
    property.setter.call(map(checkedId))
}