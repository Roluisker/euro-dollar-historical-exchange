package com.sc.lydianlion

import android.app.Application
import com.sc.core.di.ContextModule
import com.sc.core.di.CoreComponent
import com.sc.core.di.CoreComponentProvider
import com.sc.core.di.DaggerCoreComponent
import timber.log.Timber

class App : Application(), CoreComponentProvider {

    lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()
        BuildConfig.DEBUG.takeIf { true }.apply { Timber.plant(Timber.DebugTree()) }
        coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()

    }

    override fun provideCoreComponent(): CoreComponent = coreComponent

}