package com.brandongogetap.scoper.fragmentdemo.base

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule(val application: Application) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton @Named("string") fun provideString(): String {
        return "Injected String"
    }
}