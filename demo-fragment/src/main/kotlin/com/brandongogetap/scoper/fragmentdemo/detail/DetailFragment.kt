package com.brandongogetap.scoper.fragmentdemo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import com.brandongogetap.scoper.Scoper
import com.brandongogetap.scoper.fragmentdemo.R
import com.brandongogetap.scoper.fragmentdemo.base.BaseFragment
import com.brandongogetap.scoper.fragmentdemo.home.HomeComponent
import javax.inject.Inject
import javax.inject.Named


class DetailFragment : BaseFragment<DetailComponent>() {

    companion object {
        fun newInstance(parentScope: String): DetailFragment {
            val fragment = DetailFragment()
            val bundle = Bundle()
            bundle.putString("parent_scope", parentScope)
            fragment.arguments = bundle
            return fragment
        }
    }

    @field:[Inject Named("string")]
    lateinit var injectedString: String

    @BindView(R.id.tv_injected_text) lateinit var textView: TextView

    lateinit var parentScopeName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentScopeName = arguments.getString("parent_scope")!!
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_detail, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component?.inject(this)
        textView.text = injectedString
    }

    override fun initComponent(): DetailComponent {
        return Scoper.getComponentForTag<HomeComponent>(parentScopeName, context).plus(DetailModule())
    }
}