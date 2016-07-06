package com.brandongogetap.scoper.conductordemo.base

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler

import com.brandongogetap.scoper.Scoped
import com.brandongogetap.scoper.Scoper
import com.brandongogetap.scoper.ScoperContext

abstract class BaseActivity : AppCompatActivity(), Scoped, ControllerChangeHandler.ControllerChangeListener {

    override fun getScopeName(): String = javaClass.name

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ScoperContext(newBase, scopeName))
    }

    override fun onChangeStarted(to: Controller?, from: Controller?, isPush: Boolean, container: ViewGroup?, handler: ControllerChangeHandler?) {
    }

    override fun onChangeCompleted(to: Controller?, from: Controller?, isPush: Boolean, container: ViewGroup?, handler: ControllerChangeHandler?) {
        if (isPush.not()) Scoper.destroyScope((from as BaseController).getScopedContext())
    }
}
