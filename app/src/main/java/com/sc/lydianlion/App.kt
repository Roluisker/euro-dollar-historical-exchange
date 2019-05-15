package com.sc.lydianlion

import android.app.Application
import androidx.fragment.app.Fragment
import com.sc.core.BuildConfig
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        BuildConfig.DEBUG.takeIf { true }.apply { Timber.plant(Timber.DebugTree()) }
        appComponent = buildDagger()
        appComponent!!.inject(this)
    }

    private fun buildDagger(): AppComponent {
        return DaggerAppComponent.builder()
            .application(this)
            .context(this)
            .build()
    }

    override fun supportFragmentInjector() = fragmentInjector

}