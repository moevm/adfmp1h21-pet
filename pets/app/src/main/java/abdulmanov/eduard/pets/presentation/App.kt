package abdulmanov.eduard.pets.presentation

import abdulmanov.eduard.pets.dagger.components.AppComponent
import abdulmanov.eduard.pets.dagger.components.DaggerAppComponent
import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import io.reactivex.plugins.RxJavaPlugins

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        initWorkManager()
        initPicasso()
        RxJavaPlugins.setErrorHandler {}
    }

    private fun initWorkManager(){
        val configuration = Configuration.Builder()
            .setWorkerFactory(appComponent.getWorkerFactory())
            .build()

        WorkManager.initialize(applicationContext, configuration)
    }

    private fun initPicasso() {
        val picasso = Picasso.Builder(this)
            .memoryCache(LruCache(MAX_CACHE))
            .build()
        Picasso.setSingletonInstance(picasso)
    }

    companion object {
        private const val MAX_CACHE = 250_000_000
    }
}