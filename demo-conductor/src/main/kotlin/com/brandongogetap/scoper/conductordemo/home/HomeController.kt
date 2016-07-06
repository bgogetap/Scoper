package com.brandongogetap.scoper.conductordemo.home

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.brandongogetap.scoper.Scoper
import com.brandongogetap.scoper.conductordemo.MainComponent
import com.brandongogetap.scoper.conductordemo.R
import com.brandongogetap.scoper.conductordemo.base.BaseController
import com.brandongogetap.scoper.conductordemo.home.detail.DetailController

class HomeController : BaseController() {

    lateinit var component: HomeComponent

    override fun onViewBound(view: View) {
        component.inject(this)
    }

    @OnClick(R.id.btn_details) fun detailClicked() {
        goToDetail()
    }

    private fun goToDetail() {
        val detailController = DetailController()
        detailController.targetController = this
        router.pushController(RouterTransaction.builder(detailController)
                .pushChangeHandler(FadeChangeHandler())
                .popChangeHandler(FadeChangeHandler())
                .build())
    }

    override fun initComponent(activity: Activity): Any {
        component = Scoper.getComponent<MainComponent>(activity).plus(HomeModule())
        return component
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_home, container, false)
    }
}
