import 'package:video_compressor_media_codec/compress_type.dart';

import 'video_compressor_media_codec_platform_interface.dart';

class VideoCompressorMediaCodec {
  Future<String?> compressVideo(String filePath, CompressType compressType) {
    return VideoCompressorMediaCodecPlatform.instance.compressVideo(filePath, compressType);
  }

  Stream<double> compressVideoProgress() {
    return VideoCompressorMediaCodecPlatform.instance.compressVideoProgress();
  }
}
