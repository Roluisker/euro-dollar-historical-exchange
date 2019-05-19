package com.sc.timeline.di

import com.sc.timeline.ui.di.HistoricalFragmentModule
import dagger.Module

@Module(includes = [HistoricalFragmentModule::class]) // connected (sub)modules
class TimelineModule {
    // still empty, we might use this in the future for everything not view-related.
}