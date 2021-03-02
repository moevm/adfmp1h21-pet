package abdulmanov.eduard.pets.presentation.main

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.ActivityMainBinding
import abdulmanov.eduard.pets.domain.interactors.PetsInteractor
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation.Screens
import abdulmanov.eduard.pets.presentation._common.base.BaseActivity
import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val navigator: AppNavigator = AppNavigator(this, R.id.mainFragmentContainerView)

    private val viewModel by initViewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null){
            viewModel.executeTransitionProcessing()
            viewModel.executeTransitionProcessing()
        }
    }
}