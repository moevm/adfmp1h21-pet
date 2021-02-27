package abdulmanov.eduard.pets.presentation._common.base

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.jintin.bindingextension.BindingFragment
import javax.inject.Inject

open class BaseFragment<V: ViewBinding>: BindingFragment<V>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected inline fun <reified VM: ViewModel> initViewModel() = viewModels<VM> { viewModelFactory }
}