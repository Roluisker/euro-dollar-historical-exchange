package com.sc.lydianlion

import android.content.Context
import com.sc.lydianlion.provide.DataModule
import com.sc.lydianlion.ui.UiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [DataModule::class, AndroidSupportInjectionModule::class, UiModule::class])
@Singleton
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

}