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

class DetailController : BaseController<DetailComponent>() {

    override fun onViewBound(view: View) {
        component?.inject(this)
    }

    override fun initComponent(activity: Activity): DetailComponent {
        return Scoper.getComponent<HomeComponent>(getParentScopeContext()).plus(DetailModule())
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_detail, container, false)
    }
}