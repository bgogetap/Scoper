package com.brandongogetap.scoper.conductordemo.base

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife

import com.bluelinelabs.conductor.Controller
import com.brandongogetap.scoper.Scoped
import com.brandongogetap.scoper.Scoper
import com.brandongogetap.scoper.ScoperContext

abstract class BaseController<T> : Controller(), Scoped<T> {

    protected var component: T? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflateView(inflater, container)
        createComponent()
        ButterKnife.bind(this, view)
        onViewBound(view)
        return view
    }

    private fun createComponent() {
        if (component == null) {
            component = Scoper.createComponent<T>(this, initComponent(activity))
        }
    }

    fun getScopedContext(): ScoperContext = ScoperContext(activity, this)

    override fun getScopeName(): String = javaClass.name

    protected abstract fun initComponent(activity: Activity): T

    protected abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup): View

    protected abstract fun onViewBound(view: View)
}
