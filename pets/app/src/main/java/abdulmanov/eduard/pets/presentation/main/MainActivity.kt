package abdulmanov.eduard.pets.presentation.main

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.ActivityMainBinding
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation.Screens
import abdulmanov.eduard.pets.presentation._common.base.BaseActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val navigator: AppNavigator = AppNavigator(this, R.id.mainFragmentContainerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null){
            router.replaceScreen(Screens.pet())
        }
    }
}