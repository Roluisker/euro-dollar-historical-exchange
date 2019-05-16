package com.sc.lydianlion.ui.view.history

import com.sc.lydianlion.di.InjectViewModel
import com.sc.lydianlion.di.ProvideViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ProvideViewModel::class])
abstract class HistoricalModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun bind(): HistoricalFragment

}