package com.brandongogetap.scoper.conductordemo.base

import com.brandongogetap.scoper.conductordemo.MainComponent
import com.brandongogetap.scoper.conductordemo.MainModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun plus(mainModule: MainModule): MainComponent
}