import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:video_compressor_media_codec/video_compressor_media_codec_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelVideoCompressorMediaCodec platform = MethodChannelVideoCompressorMediaCodec();
  const MethodChannel channel = MethodChannel('video_compressor_media_codec');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });
}
