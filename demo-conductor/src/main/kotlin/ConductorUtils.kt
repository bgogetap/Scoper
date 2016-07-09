import android.content.Context
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.brandongogetap.scoper.conductordemo.base.BaseController

fun Router.pushNormal(controller: Controller) {
    this.pushController(RouterTransaction.builder(controller)
            .pushChangeHandler(FadeChangeHandler())
            .popChangeHandler(FadeChangeHandler())
            .build())
}

fun Controller.getParentScopeContext(): Context {
    return (this.targetController as BaseController<*>).getScopedContext()
}
