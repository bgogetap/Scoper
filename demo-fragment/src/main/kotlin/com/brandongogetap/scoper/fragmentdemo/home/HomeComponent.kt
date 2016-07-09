package com.brandongogetap.scoper.fragmentdemo.home

import com.brandongogetap.scoper.fragmentdemo.detail.DetailComponent
import com.brandongogetap.scoper.fragmentdemo.detail.DetailModule
import dagger.Subcomponent

@HomeScope
@Subcomponent(modules = arrayOf(HomeModule::class))
interface HomeComponent {

    fun inject(homeFragment: HomeFragment)

    fun plus(detailModule: DetailModule): DetailComponent
}