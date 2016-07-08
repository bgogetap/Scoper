package com.brandongogetap.scoper.conductordemo

import android.os.Bundle
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.brandongogetap.scoper.Scoper
import com.brandongogetap.scoper.conductordemo.base.ApplicationComponent
import com.brandongogetap.scoper.conductordemo.base.BaseActivity
import com.brandongogetap.scoper.conductordemo.base.MyApplication
import com.brandongogetap.scoper.conductordemo.home.HomeController

class MainActivity : BaseActivity() {

    @BindView(R.id.container) lateinit var container: ViewGroup

    private lateinit var router: Router
    private lateinit var component: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        initComponent().inject(this)

        router = Conductor.attachRouter(this, container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(HomeController())
        }
        router.addChangeListener(this)
    }

    private fun initComponent(): MainComponent {
        component = Scoper.createComponent(this,
                Scoper.getComponentForTag<ApplicationComponent>(MyApplication.SCOPE_TAG, this)
                        .plus(MainModule()))
        return component
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
