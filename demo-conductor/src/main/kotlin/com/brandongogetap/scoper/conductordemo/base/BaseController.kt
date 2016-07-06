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

abstract class BaseController : Controller(), Scoped {

    private var componentCreated = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflateView(inflater, container)
        createComponent()
        ButterKnife.bind(this, view)
        onViewBound(view)
        return view
    }

    private fun createComponent() {
        if (componentCreated.not()) {
            Scoper.createComponent<Any>(getScopedContext(), initComponent(activity))
            componentCreated = true
        }
    }

    fun getScopedContext(): ScoperContext = ScoperContext(activity, this)

    override fun getScopeName(): String = javaClass.name

    protected abstract fun initComponent(activity: Activity): Any

    protected abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup): View

    protected abstract fun onViewBound(view: View)
}
