package com.sc.lydianlion.di

import androidx.lifecycle.ViewModel
import com.sc.lydianlion.core.annotation.ViewModelKey
import com.sc.lydianlion.repository.history.HistoricalRepository
import com.sc.lydianlion.ui.view.history.HistoricalViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ProvideViewModel {

    @Provides
    @IntoMap
    @ViewModelKey(HistoricalViewModel::class)
    fun provideHistoricalViewModel(historicalRepository: HistoricalRepository): ViewModel =
        HistoricalViewModel(historicalRepository)

}