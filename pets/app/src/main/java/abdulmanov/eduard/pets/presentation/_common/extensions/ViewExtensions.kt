package abdulmanov.eduard.pets.presentation._common.extensions

import abdulmanov.eduard.pets.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.text.InputType
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.switchmaterial.SwitchMaterial
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun EditText.bind(action: (String) -> Unit) = addTextChangedListener { action(it.toString()) }

fun RadioGroup.bind(action: (Any) -> Unit, map: (Int) -> Any) = setOnCheckedChangeListener { _, checkedId ->
    action(map(checkedId))
}

fun SwitchMaterial.bind(action: (Boolean) -> Unit) = setOnCheckedChangeListener { _, checked -> action(checked) }

fun AutoCompleteTextView.bind(action: (Any) -> Unit, map: (Int) -> Any) = addTextChangedListener {
    var index: Int = -1
    for(i in 0 until adapter.count){
        val item = adapter.getItem(i) as String
        if(item == it.toString()){
            index = i
            break
        }
    }
    action(map(index))
}

fun EditText.setMultiLineCapSentencesAndDoneAction() {
    imeOptions = EditorInfo.IME_ACTION_DONE
    setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_FLAG_MULTI_LINE)
}

fun BottomSheetDialog.setStateExpanded(){
    setOnShowListener {
        val dialog = it as BottomSheetDialog
        val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
        BottomSheetBehavior.from(bottomSheet).isHideable = true
    }
}

@SuppressLint("ClickableViewAccessibility")
fun AutoCompleteTextView.initSpinner(items: List<String>, layout: Int = R.layout.item_spinner) {
    setAdapter(ArrayAdapter(context, layout, items))
    setOnTouchListener { _, _ -> return@setOnTouchListener true }
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

fun Int.dpToPx() = this * Resources.getSystem().displayMetrics.density

fun ImageView.loadImg(
    imageUri: String?,
    placeholderRes: Int? = R.drawable.ic_photo_placeholder,
    errorRes: Int? = R.drawable.ic_photo_placeholder,
) {
    if (imageUri.isNullOrEmpty()) {
        this.setImageResource(errorRes ?: 0)
        return
    }

    Picasso.get().load(Uri.parse(imageUri)).apply {
        fit()
        centerCrop()
        placeholderRes?.let { placeholder(it) }
        errorRes?.let { error(it) }
        into(this@loadImg)
    }
}