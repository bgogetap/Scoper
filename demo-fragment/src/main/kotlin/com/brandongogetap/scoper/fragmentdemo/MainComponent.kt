package com.brandongogetap.scoper.fragmentdemo

import com.brandongogetap.scoper.fragmentdemo.home.HomeComponent
import com.brandongogetap.scoper.fragmentdemo.home.HomeModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {

    fun inject(activity: MainActivity)

    fun plus(homeModule: HomeModule): HomeComponent
}