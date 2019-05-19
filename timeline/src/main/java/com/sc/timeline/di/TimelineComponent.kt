package com.sc.timeline.di

import com.ironflowers.fbt2.core.di.scope.FragmentScope
import com.sc.core.di.CoreComponent
import com.sc.timeline.ui.HistoricalFragment
import dagger.Component

@FragmentScope
@Component(
    modules = [TimelineModule::class],
    dependencies = [CoreComponent::class]
)
interface TimelineComponent {
    fun inject(overviewFragment: HistoricalFragment)
}