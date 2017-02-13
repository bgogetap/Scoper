package com.brandongogetap.scoper.fragmentdemo.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.brandongogetap.scoper.Scoped
import com.brandongogetap.scoper.Scoper
import com.brandongogetap.scoper.ScoperContext

abstract class BaseActivity<T> : AppCompatActivity(), Scoped {

    protected var component: T? = null

    override fun getScopeName(): String = javaClass.name

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ScoperContext(newBase, scopeName))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = Scoper.createComponent(this, initComponent())
    }

    abstract fun initComponent(): T
}
