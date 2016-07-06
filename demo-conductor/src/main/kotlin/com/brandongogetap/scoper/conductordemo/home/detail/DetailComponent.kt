package com.brandongogetap.scoper.conductordemo.home.detail

import dagger.Subcomponent

@DetailScope
@Subcomponent(modules = arrayOf(DetailModule::class))
interface DetailComponent {

    fun inject(detailController: DetailController)
}