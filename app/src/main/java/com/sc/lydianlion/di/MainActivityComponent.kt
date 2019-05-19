package com.sc.lydianlion.di

import com.ironflowers.fbt2.core.di.scope.ActivityScope
import com.sc.core.di.CoreComponent
import com.sc.lydianlion.MainActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [MainActivityModule::class],
    dependencies = [CoreComponent::class]
)
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
}