import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:video_compressor_media_codec/src/compress_type.dart';
import 'package:video_compressor_media_codec/src/video_compressor_media_codec_method_channel.dart';

abstract class VideoCompressorMediaCodecPlatform extends PlatformInterface {
  /// Constructs a VideoCompressorMediaCodecPlatform.
  VideoCompressorMediaCodecPlatform() : super(token: _token);

  static final Object _token = Object();

  static VideoCompressorMediaCodecPlatform _instance = MethodChannelVideoCompressorMediaCodec();

  /// The default instance of [VideoCompressorMediaCodecPlatform] to use.
  ///
  /// Defaults to [MethodChannelVideoCompressorMediaCodec].
  static VideoCompressorMediaCodecPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [VideoCompressorMediaCodecPlatform] when
  /// they register themselves.
  static set instance(VideoCompressorMediaCodecPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> compressVideo(String filePath, CompressType compressType) {
    throw UnimplementedError('compressVideo() has not been implemented.');
  }

  Future<bool> cancelCompressVideo() {
    throw UnimplementedError('cancelCompressVideo() has not been implemented.');
  }

  Stream<double> compressVideoProgress() {
    throw UnimplementedError('compressVideoProgress() has not been implemented.');
  }
}
