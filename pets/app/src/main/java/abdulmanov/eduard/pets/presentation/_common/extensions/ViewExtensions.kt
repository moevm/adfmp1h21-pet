package abdulmanov.eduard.pets.presentation._common.extensions

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener

fun EditText.bind(action: (String) -> Unit) = addTextChangedListener { action(it.toString()) }

fun RadioGroup.bind(action: (Any) -> Unit, map: (Int) -> Any) = setOnCheckedChangeListener { _, checkedId ->
    action(map(checkedId))
}

fun TextView.setTextColorRes(@ColorRes color:Int){
    setTextColor(ContextCompat.getColor(context, color))
}

fun Context.getScreenSize(): Point {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = wm.currentWindowMetrics
        val inserts = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        val width = windowMetrics.bounds.width() - inserts.left - inserts.right
        val height = windowMetrics.bounds.height() - inserts.top - inserts.bottom
        Point(width, height)
    }else {
        Point().apply { wm.defaultDisplay.getSize(this) }
    }
}