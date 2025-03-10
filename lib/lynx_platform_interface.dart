import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'lynx_method_channel.dart';

abstract class LynxPlatform extends PlatformInterface {
  /// Constructs a LynxPlatform.
  LynxPlatform() : super(token: _token);

  static final Object _token = Object();

  static LynxPlatform _instance = MethodChannelLynx();

  /// The default instance of [LynxPlatform] to use.
  ///
  /// Defaults to [MethodChannelLynx].
  static LynxPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [LynxPlatform] when
  /// they register themselves.
  static set instance(LynxPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    return _instance.getPlatformVersion();
  }
}
