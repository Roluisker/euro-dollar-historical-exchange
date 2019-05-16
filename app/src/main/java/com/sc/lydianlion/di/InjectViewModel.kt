package com.sc.lydianlion.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sc.lydianlion.ui.view.history.HistoricalFragment
import com.sc.lydianlion.ui.view.history.HistoricalViewModel
import dagger.Module
import dagger.Provides

@Module
class InjectViewModel {

    @Provides
    fun provideHistoricalViewModel(
        factory: ViewModelProvider.Factory,
        target: HistoricalFragment
    ): HistoricalViewModel =
        ViewModelProviders.of(target, factory).get(HistoricalViewModel::class.java)

}