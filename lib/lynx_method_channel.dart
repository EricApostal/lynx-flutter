import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'lynx_platform_interface.dart';

/// An implementation of [LynxPlatform] that uses method channels.
class MethodChannelLynx extends LynxPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('lynx');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
