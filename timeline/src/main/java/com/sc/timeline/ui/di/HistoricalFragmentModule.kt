package com.sc.timeline.ui.di

import com.sc.core.api.MoneyApi
import com.sc.core.db.TimeSeriesDao
import com.sc.core.ui.getViewModel
import com.sc.timeline.ui.HistoricalViewModel
import com.sc.timeline.repository.HistoricalRepositoryImpl
import com.sc.timeline.ui.HistoricalFragment
import dagger.Module
import dagger.Provides

@Module
class HistoricalFragmentModule(private val fragment: HistoricalFragment) {

    @Provides
    fun provideHistoricalRepository(
        moneyApi: MoneyApi,
        timeSeriesDao: TimeSeriesDao
    ): HistoricalRepositoryImpl {
        return HistoricalRepositoryImpl(moneyApi, timeSeriesDao)
    }

    @Provides
    fun provideHistoricalViewModel(
        repository: HistoricalRepositoryImpl
    ): HistoricalViewModel =
        fragment.getViewModel { HistoricalViewModel(repository) }

}