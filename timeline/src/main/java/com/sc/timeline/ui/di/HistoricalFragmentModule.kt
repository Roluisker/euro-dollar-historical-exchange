package com.sc.timeline.ui.di

import com.sc.core.api.MoneyApi
import com.sc.core.ui.getViewModel
import com.sc.timeline.ui.HistoricalViewModel
import com.sc.timeline.repository.history.HistoricalRepositoryImpl
import com.sc.timeline.ui.HistoricalFragment
import dagger.Module
import dagger.Provides

@Module
class HistoricalFragmentModule(val fragment: HistoricalFragment) {

    @Provides
    fun provideHistoricalRepository(moneyApi: MoneyApi): HistoricalRepositoryImpl {
        return HistoricalRepositoryImpl(moneyApi)
    }

    @Provides
    fun provideHistoricalViewModel(
        repository: HistoricalRepositoryImpl
    ): HistoricalViewModel =
        fragment.getViewModel {
            HistoricalViewModel(repository)
        }

}