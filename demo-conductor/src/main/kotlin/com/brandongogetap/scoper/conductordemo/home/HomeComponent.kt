package com.brandongogetap.scoper.conductordemo.home

import com.brandongogetap.scoper.conductordemo.home.detail.DetailComponent
import com.brandongogetap.scoper.conductordemo.home.detail.DetailModule
import dagger.Subcomponent

@HomeScope
@Subcomponent(modules = arrayOf(HomeModule::class))
interface HomeComponent {

    fun inject(controller: HomeController)

    fun plus(detailModule: DetailModule): DetailComponent
}