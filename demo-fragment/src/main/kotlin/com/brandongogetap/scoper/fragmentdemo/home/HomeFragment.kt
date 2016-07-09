package com.brandongogetap.scoper.fragmentdemo.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import com.brandongogetap.scoper.Scoper
import com.brandongogetap.scoper.fragmentdemo.MainComponent
import com.brandongogetap.scoper.fragmentdemo.R
import com.brandongogetap.scoper.fragmentdemo.base.BaseFragment
import com.brandongogetap.scoper.fragmentdemo.detail.DetailFragment

class HomeFragment : BaseFragment<HomeComponent>() {

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_home, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component?.inject(this)
    }

    override fun initComponent(): HomeComponent {
        return Scoper.getComponent<MainComponent>(activity).plus(HomeModule())
    }

    @OnClick(R.id.btn_details) fun detailsClicked() {
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailFragment.newInstance(scopeName))
                .addToBackStack(null)
                .commit()
    }
}