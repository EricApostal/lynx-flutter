import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class LynxView extends StatefulWidget {
  const LynxView({Key? key}) : super(key: key);

  @override
  State<LynxView> createState() => _LynxViewState();
}

class _LynxViewState extends State<LynxView> {
  static const MethodChannel _channel = MethodChannel('lynx');
  int? _textureId;

  @override
  void initState() {
    super.initState();
    _getTextureId();
  }

  Future<void> _getTextureId() async {
    try {
      final id = await _channel.invokeMethod<int>('getTextureId');
      if (mounted) {
        setState(() {
          _textureId = id;
        });
      }
    } catch (e) {
      debugPrint('Error getting texture ID: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    if (_textureId == null) {
      return const Center(child: CircularProgressIndicator());
    }

    return Texture(textureId: _textureId!);
  }
}
