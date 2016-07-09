package com.brandongogetap.scoper.fragmentdemo.base

import com.brandongogetap.scoper.fragmentdemo.MainComponent
import com.brandongogetap.scoper.fragmentdemo.MainModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun plus(mainModule: MainModule): MainComponent
}