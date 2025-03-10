import 'package:flutter_test/flutter_test.dart';
import 'package:lynx/lynx.dart';
import 'package:lynx/lynx_platform_interface.dart';
import 'package:lynx/lynx_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockLynxPlatform
    with MockPlatformInterfaceMixin
    implements LynxPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final LynxPlatform initialPlatform = LynxPlatform.instance;

  test('$MethodChannelLynx is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelLynx>());
  });

  test('getPlatformVersion', () async {
    Lynx lynxPlugin = Lynx();
    MockLynxPlatform fakePlatform = MockLynxPlatform();
    LynxPlatform.instance = fakePlatform;

    expect(await lynxPlugin.getPlatformVersion(), '42');
  });
}
