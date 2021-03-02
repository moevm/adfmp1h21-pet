package abdulmanov.eduard.pets.presentation._common.view

import abdulmanov.eduard.pets.R
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import org.w3c.dom.Text

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val title: TextView
    private val progress: ProgressBar

    init {
        inflate(context, R.layout.progress_button, this)

        title = findViewById(R.id.title)
        progress = findViewById(R.id.progressBar)

        context.withStyledAttributes(attrs, R.styleable.ProgressButton){
            val text = getResourceId(R.styleable.ProgressButton_text, 0)
            if(text != 0 )
                title.setText(text)
        }
    }

    fun setText(@StringRes resId: Int){
        title.setText(resId)
    }

    fun showProgress(show: Boolean) {
        title.isVisible = !show
        progress.isVisible = show
    }
}