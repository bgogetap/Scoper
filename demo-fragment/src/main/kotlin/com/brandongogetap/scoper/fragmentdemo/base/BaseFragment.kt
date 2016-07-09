package com.brandongogetap.scoper.fragmentdemo.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.brandongogetap.scoper.Scoped
import com.brandongogetap.scoper.Scoper
import com.brandongogetap.scoper.ScoperContext

abstract class BaseFragment<T> : Fragment(), Scoped<T> {

    private lateinit var unbinder: Unbinder

    protected var component: T? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (component == null) {
            component = Scoper.createComponent<T>(context, initComponent())
        }
        unbinder = ButterKnife.bind(this, view!!)
    }

    override fun getContext(): Context {
        return ScoperContext(activity, scopeName)
    }

    override fun getScopeName(): String = javaClass.name

    abstract fun initComponent(): T

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        //TODO: Better way to determine that Fragment is being permanently destroyed?
        if (!activity.isChangingConfigurations) {
            Scoper.destroyScope(context)
        }
    }
}