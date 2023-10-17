import 'package:flutter_test/flutter_test.dart';
import 'package:video_compressor_media_codec/compress_type.dart';
import 'package:video_compressor_media_codec/video_compressor_media_codec.dart';
import 'package:video_compressor_media_codec/video_compressor_media_codec_platform_interface.dart';
import 'package:video_compressor_media_codec/video_compressor_media_codec_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockVideoCompressorMediaCodecPlatform
    with MockPlatformInterfaceMixin
    implements VideoCompressorMediaCodecPlatform {
  @override
  Future<String?> compressVideo(String filePath, CompressType compressType) {
    // TODO: implement compressVideo
    throw UnimplementedError();
  }

  @override
  Stream<double> compressVideoProgress() {
    // TODO: implement compressVideoProgress
    throw UnimplementedError();
  }
}

void main() {
  final VideoCompressorMediaCodecPlatform initialPlatform = VideoCompressorMediaCodecPlatform.instance;

  test('$MethodChannelVideoCompressorMediaCodec is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelVideoCompressorMediaCodec>());
  });

  test('getPlatformVersion', () async {
    VideoCompressorMediaCodec videoCompressorMediaCodecPlugin = VideoCompressorMediaCodec();
    MockVideoCompressorMediaCodecPlatform fakePlatform = MockVideoCompressorMediaCodecPlatform();
    VideoCompressorMediaCodecPlatform.instance = fakePlatform;
  });
}
