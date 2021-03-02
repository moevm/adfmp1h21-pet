package abdulmanov.eduard.pets.presentation._common.extensions

import android.widget.EditText
import android.widget.RadioGroup
import androidx.core.widget.addTextChangedListener
import kotlin.reflect.KMutableProperty

fun EditText.bind(action: (String) -> Unit) = addTextChangedListener { action(it.toString()) }

fun RadioGroup.bind(action: (Any) -> Unit, map:(Int) -> Any) = setOnCheckedChangeListener { _, checkedId ->
    action(map(checkedId))
}