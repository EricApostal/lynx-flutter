package dev.flutter.example

import DemoTemplateProvider
import android.content.Context
import android.view.View
import com.lynx.tasm.LynxView
import com.lynx.tasm.LynxViewBuilder
import io.flutter.plugin.platform.PlatformView

internal class NativeView(context: Context, id: Int, creationParams: Map<String, Any>) :
        PlatformView {
    private val lynxView: LynxView

    override fun getView(): View {
        return lynxView
    }

    override fun dispose() {}

    init {
        // textView = TextView(context)
        // textView.textSize = 72f
        // textView.setBackgroundColor(Color.rgb(255, 255, 255))
        // textView.text = "Rendered on a native Android view (id: $id)"

        lynxView = buildLynxView(context)

        val uri = "main.lynx.bundle"
        lynxView.renderTemplateUrl(uri, "")
    }

    private fun buildLynxView(context: Context): LynxView {
        val viewBuilder = LynxViewBuilder()

        viewBuilder.setTemplateProvider(DemoTemplateProvider(context))
        return viewBuilder.build(context)
    }
}
