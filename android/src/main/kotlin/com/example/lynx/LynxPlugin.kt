package com.example.lynx

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.view.Surface
import android.view.View
import androidx.annotation.ColorInt
import com.lynx.tasm.LynxEnv
import com.lynx.tasm.LynxView
import com.lynx.tasm.LynxViewBuilder
import com.lynx.tasm.provider.AbsTemplateProvider
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.view.TextureRegistry
import java.io.ByteArrayOutputStream
import java.io.IOException

/** LynxPlugin */
class LynxPlugin : FlutterPlugin, MethodCallHandler {
  private lateinit var channel: MethodChannel
  private var textureEntry: TextureRegistry.SurfaceTextureEntry? = null
  private var lynxView: LynxView? = null

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "lynx")
    channel.setMethodCallHandler(this)

    val applicationContext = flutterPluginBinding.applicationContext as Application
    LynxEnv.inst().init(applicationContext, null, null, null)

    textureEntry = flutterPluginBinding.textureRegistry.createSurfaceTexture()
    val surfaceTexture = textureEntry?.surfaceTexture()
    val surface = Surface(surfaceTexture)

    val viewBuilder = LynxViewBuilder()
    viewBuilder.setTemplateProvider(DemoTemplateProvider(applicationContext))
    viewBuilder.setPresetMeasuredSpec(
            View.MeasureSpec.makeMeasureSpec(720, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(1280, View.MeasureSpec.EXACTLY)
    )

    lynxView = viewBuilder.build(applicationContext)
    lynxView?.setBackgroundColor(Color.BLUE);

    val uri = "main.lynx.bundle"
    lynxView?.renderTemplateUrl(uri, "")

    flutterPluginBinding.platformViewRegistry.registerViewFactory(
            "lynx_view",
            LynxTextureViewFactory(textureEntry!!.id())
    )
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if (call.method == "getTextureId") {
      result.success(textureEntry?.id())
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)

    lynxView?.destroy()
    textureEntry?.release()
  }
}

class LynxTextureViewFactory(private val textureId: Long) :
        io.flutter.plugin.platform.PlatformViewFactory(
                io.flutter.plugin.common.StandardMessageCodec.INSTANCE
        ) {

  override fun create(
          context: android.content.Context,
          viewId: Int,
          args: Any?
  ): io.flutter.plugin.platform.PlatformView {
    return LynxTextureView(textureId)
  }
}

class LynxTextureView(private val textureId: Long) : io.flutter.plugin.platform.PlatformView {
  override fun getView(): android.view.View? = null
  override fun dispose() {}

  fun getTextureId(): Long = textureId
}

class DemoTemplateProvider(context: Context) : AbsTemplateProvider() {

  private var mContext: Context = context.applicationContext

  override fun loadTemplate(uri: String, callback: Callback) {
    Thread {
              try {
                mContext.assets.open(uri).use { inputStream ->
                  ByteArrayOutputStream().use { byteArrayOutputStream ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while ((inputStream.read(buffer).also { length = it }) != -1) {
                      byteArrayOutputStream.write(buffer, 0, length)
                    }
                    callback.onSuccess(byteArrayOutputStream.toByteArray())
                  }
                }
              } catch (e: IOException) {
                callback.onFailed(e.message)
              }
            }
            .start()
  }
}
