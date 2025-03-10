package com.example.lynx

import android.app.Application
import com.lynx.tasm.LynxEnv
import dev.flutter.example.NativeViewFactory
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** LynxPlugin */
class LynxPlugin : FlutterPlugin, MethodCallHandler {
  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    val applicationContext = flutterPluginBinding.applicationContext as Application
    LynxEnv.inst().init(applicationContext, null, null, null)

    flutterPluginBinding.platformViewRegistry.registerViewFactory("lynx_view", NativeViewFactory())
  }

  override fun onMethodCall(call: MethodCall, result: Result) {}

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {}
}
