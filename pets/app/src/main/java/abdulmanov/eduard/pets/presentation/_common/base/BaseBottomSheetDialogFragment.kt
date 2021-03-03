package abdulmanov.eduard.pets.presentation._common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

open class BaseBottomSheetDialogFragment<V : ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: V? = null

    val binding: V
        get() = _binding
            ?: throw RuntimeException("Should only use binding after onCreateView and before onDestroyView")

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected inline fun <reified VM : ViewModel> initViewModel() = viewModels<VM> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun <V : ViewBinding> getBinding(inflater: LayoutInflater, container: ViewGroup?): V {
        return findClass().getBinding(inflater, container)
    }

    private fun Any.findClass(): Class<*> {
        var javaClass: Class<*> = this.javaClass
        var result: Class<*>? = null
        while (result == null || !result.checkMethod()) {
            result = (javaClass
                .genericSuperclass as? ParameterizedType)
                ?.actualTypeArguments?.firstOrNull {
                    if (it is Class<*>) {
                        it.checkMethod()
                    } else {
                        false
                    }
                } as? Class<*>
            javaClass = javaClass.superclass
        }
        return result
    }

    private fun Class<*>.checkMethod(): Boolean {
        return try {
            getMethod(
                "inflate",
                LayoutInflater::class.java
            )
            true
        } catch (ex: Exception) {
            false
        }
    }

    private fun <V : ViewBinding> Class<*>.getBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): V {
        return try {
            @Suppress("UNCHECKED_CAST")
            getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            ).invoke(null, layoutInflater, container, false) as V
        } catch (ex: Exception) {
            throw RuntimeException("The ViewBinding inflate function has been changed.", ex)
        }
    }
}