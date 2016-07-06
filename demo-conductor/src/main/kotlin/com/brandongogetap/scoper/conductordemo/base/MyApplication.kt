package com.brandongogetap.scoper.conductordemo.base

import android.app.Application
import android.content.Context
import com.brandongogetap.scoper.Scoper
import com.brandongogetap.scoper.ScoperCache
import com.brandongogetap.scoper.ScoperContext

class MyApplication : Application() {

    private lateinit var scoperCache: ScoperCache

    override fun onCreate() {
        super.onCreate()
        scoperCache = ScoperCache()
        Scoper.createComponent<ApplicationComponent>(this, DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build())
    }

    /**
     * Overriding this method allows you to retrieve the [ApplicationComponent] using
     * [getApplicationContext]
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(ScoperContext(base, "application_scope"))
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
