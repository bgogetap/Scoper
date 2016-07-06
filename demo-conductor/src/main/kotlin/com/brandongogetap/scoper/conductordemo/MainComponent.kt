package com.brandongogetap.scoper.conductordemo

import com.brandongogetap.scoper.conductordemo.home.HomeComponent
import com.brandongogetap.scoper.conductordemo.home.HomeModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {

    fun inject(activity: MainActivity)

    fun plus(homeModule: HomeModule): HomeComponent
}