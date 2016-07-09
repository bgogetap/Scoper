package com.brandongogetap.scoper.fragmentdemo.detail

import dagger.Subcomponent

@DetailScope
@Subcomponent(modules = arrayOf(DetailModule::class))
interface DetailComponent {

    fun inject(detailFragment: DetailFragment)
}