package com.sc.timeline.ui.di

import android.content.Context
import com.sc.core.api.MoneyApi
import com.sc.core.ui.getViewModel
import com.sc.timeline.ui.HistoricalViewModel
import com.sc.timeline.repository.HistoricalRepositoryImpl
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
        repository: HistoricalRepositoryImpl, context: Context
    ): HistoricalViewModel =
        fragment.getViewModel {
            HistoricalViewModel(repository, context)
        }

}