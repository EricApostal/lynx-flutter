import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class LynxView extends StatefulWidget {
  const LynxView({Key? key}) : super(key: key);

  @override
  State<LynxView> createState() => _LynxViewState();
}

class _LynxViewState extends State<LynxView> {
  int? _textureId;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return AndroidView(
      viewType: 'lynx_view',
      creationParams: {},
      creationParamsCodec: const StandardMessageCodec(),
    );
  }
}
