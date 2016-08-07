package com.brandongogetap.scoper.fragmentdemo.base

import android.app.Application
import com.brandongogetap.scoper.Scoper

class MyApplication : Application() {

    companion object {
        const val SCOPE_TAG = "application_scope"
    }

    override fun onCreate() {
        super.onCreate()
        Scoper.cacheComponent<ApplicationComponent>(SCOPE_TAG, DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build())
    }
}
