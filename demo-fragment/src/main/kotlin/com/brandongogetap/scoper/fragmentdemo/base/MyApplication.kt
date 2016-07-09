package com.brandongogetap.scoper.fragmentdemo.base

import android.app.Application
import android.content.Context
import com.brandongogetap.scoper.Scoper
import com.brandongogetap.scoper.ScoperCache

class MyApplication : Application() {

    companion object {
        const val SCOPE_TAG = "application_scope"
    }

    private lateinit var scoperCache: ScoperCache

    override fun onCreate() {
        super.onCreate()
        scoperCache = ScoperCache()
        Scoper.cacheComponent(this, SCOPE_TAG, DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build())
    }

    /**
     * This allows [Scoper] to access the [ScoperCache] instance from any [Context]
     */
    override fun getSystemService(name: String?): Any {
        if (name.equals(ScoperCache.SERVICE_NAME)) {
            return scoperCache
        }
        return super.getSystemService(name)
    }
}
