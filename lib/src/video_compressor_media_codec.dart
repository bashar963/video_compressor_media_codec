import 'package:video_compressor_media_codec/src/compress_type.dart';
import 'package:video_compressor_media_codec/src/video_compressor_media_codec_platform_interface.dart';

class VideoCompressorMediaCodec {
  Future<String?> compressVideo(String filePath, CompressType compressType) {
    return VideoCompressorMediaCodecPlatform.instance.compressVideo(filePath, compressType);
  }

  Future<bool> cancelCompressVideo() {
    return VideoCompressorMediaCodecPlatform.instance.cancelCompressVideo();
  }

  Stream<double> compressVideoProgress() {
    return VideoCompressorMediaCodecPlatform.instance.compressVideoProgress();
  }
}
