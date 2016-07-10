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

class MainActivity : BaseActivity<MainComponent>() {

    @BindView(R.id.container) lateinit var container: ViewGroup

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        component?.inject(this)

        router = Conductor.attachRouter(this, container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(HomeController())
        }
        router.addChangeListener(this)
    }

    override fun initComponent(): MainComponent {
        return Scoper.getComponentForName<ApplicationComponent>(MyApplication.SCOPE_TAG)
                .plus(MainModule())
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
