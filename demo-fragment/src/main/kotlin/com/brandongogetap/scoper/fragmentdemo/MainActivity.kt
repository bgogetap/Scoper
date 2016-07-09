package com.brandongogetap.scoper.fragmentdemo

import android.os.Bundle
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.brandongogetap.scoper.Scoper
import com.brandongogetap.scoper.fragmentdemo.base.ApplicationComponent
import com.brandongogetap.scoper.fragmentdemo.base.BaseActivity
import com.brandongogetap.scoper.fragmentdemo.base.MyApplication
import com.brandongogetap.scoper.fragmentdemo.home.HomeFragment

class MainActivity : BaseActivity<MainComponent>() {

    @BindView(R.id.container) lateinit var container: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        component?.inject(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, HomeFragment.newInstance())
                    .commit()
        }
    }

    override fun initComponent(): MainComponent {
        return Scoper.getComponentForTag<ApplicationComponent>(MyApplication.SCOPE_TAG, this)
                .plus(MainModule())
    }
}
