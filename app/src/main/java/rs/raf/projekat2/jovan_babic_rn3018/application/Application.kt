package rs.raf.projekat2.jovan_babic_rn3018.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rs.raf.projekat2.jovan_babic_rn3018.modules.coreModule
import rs.raf.projekat2.jovan_babic_rn3018.modules.recipeModule
import timber.log.Timber

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        val modules = listOf(
                coreModule,
                recipeModule
        )
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            androidFileProperties()
            fragmentFactory()
            modules(modules)
        }
    }

}