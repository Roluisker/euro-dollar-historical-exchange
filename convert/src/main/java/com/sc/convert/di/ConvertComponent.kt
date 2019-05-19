package com.sc.convert.di

import com.ironflowers.fbt2.core.di.scope.FragmentScope
import com.sc.convert.ui.ConvertFragment
import com.sc.core.di.CoreComponent
import dagger.Component

@FragmentScope
@Component(
    modules = [ConvertModule::class],
    dependencies = [CoreComponent::class]
)
interface ConvertComponent {
    fun inject(detailFragment: ConvertFragment)
}