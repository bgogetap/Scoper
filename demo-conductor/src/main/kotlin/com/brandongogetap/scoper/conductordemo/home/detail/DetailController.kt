package com.brandongogetap.scoper.conductordemo.home.detail

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brandongogetap.scoper.Scoper
import com.brandongogetap.scoper.conductordemo.R
import com.brandongogetap.scoper.conductordemo.base.BaseController
import com.brandongogetap.scoper.conductordemo.home.HomeComponent
import getParentScopeContext

class DetailController : BaseController() {

    private lateinit var component: DetailComponent

    override fun onViewBound(view: View) {

    }

    override fun initComponent(activity: Activity): Any {
        component = Scoper.getComponent<HomeComponent>(getParentScopeContext()).plus(DetailModule())
        return component
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_detail, container, false)
    }
}