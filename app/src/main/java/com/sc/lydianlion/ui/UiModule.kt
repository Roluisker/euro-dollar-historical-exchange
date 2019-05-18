package com.sc.lydianlion.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sc.lydianlion.core.di.AppViewModelFactory
import com.sc.lydianlion.ui.view.history.HistoricalModule
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module(includes = [HistoricalModule::class])
class UiModule{

    @Provides
    fun provideViewModelFactory(providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>):
            ViewModelProvider.Factory = AppViewModelFactory(providers)

}