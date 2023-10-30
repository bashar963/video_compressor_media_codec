import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:path_provider/path_provider.dart';
import 'package:video_compressor_media_codec/src/compress_type.dart';
import 'package:path/path.dart' as path;
import 'package:video_compressor_media_codec/src/video_compressor_media_codec_platform_interface.dart';

/// An implementation of [VideoCompressorMediaCodecPlatform] that uses method channels.
class MethodChannelVideoCompressorMediaCodec extends VideoCompressorMediaCodecPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('video_compressor_media_codec');
  final EventChannel _progressEventChannel = const EventChannel('video_compressor_media_codec/compressVideoProgress');

  @override
  Future<String?> compressVideo(String filePath, CompressType compressType) async {
    final tempDir = await getTemporaryDirectory();
    String tempPath = tempDir.path;
    String fileName = path.basename(filePath);

    final args = {
      'filePath': filePath,
      'compressType': compressType.name,
      'outputFilePath': '$tempPath/$fileName',
    };
    final version = await methodChannel.invokeMethod<String>('compressVideo', args);
    return version;
  }

  @override
  Future<bool> cancelCompressVideo() async {
    final cancelled = await methodChannel.invokeMethod<bool>('cancelVideoCompressTask');
    return cancelled ?? false;
  }

  @override
  Stream<double> compressVideoProgress() =>
      _progressEventChannel.receiveBroadcastStream().map((event) => event as double);
}
