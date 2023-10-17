package com.bashar963.video_compressor_media_codec.videocompressor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoCompress {

    companion object {
       // private const val TAG = "VideoCompress"

        suspend fun compressVideoHigh(srcPath: String, destPath: String, listener: CompressListener) {
            try {
                val result = withContext(Dispatchers.IO) {
                    VideoController.getInstance().convertVideo(
                        srcPath, destPath, VideoController.COMPRESS_QUALITY_HIGH
                    ) { percent ->
                        listener.onProgress(percent)
                    }
                }

                if (result.isNotEmpty()) {
                    listener.onSuccess(result)
                } else {
                    listener.onFail()
                }
            } catch (e: Exception) {
                listener.onFail()
            }
        }
        suspend fun compressVideoMedium(srcPath: String, destPath: String, listener: CompressListener) {
            try {
                val result = withContext(Dispatchers.IO) {
                    VideoController.getInstance().convertVideo(
                        srcPath, destPath, VideoController.COMPRESS_QUALITY_MEDIUM
                    ) { percent ->
                        listener.onProgress(percent)
                    }
                }

                if (result.isNotEmpty()) {
                    listener.onSuccess(result)
                } else {
                    listener.onFail()
                }
            } catch (e: Exception) {
                listener.onFail()
            }
        }
        suspend fun compressVideoLow(srcPath: String, destPath: String, listener: CompressListener) {
            try {
                val result = withContext(Dispatchers.IO) {
                    VideoController.getInstance().convertVideo(
                        srcPath, destPath, VideoController.COMPRESS_QUALITY_LOW
                    ) { percent ->
                        listener.onProgress(percent)
                    }
                }

                if (result.isNotEmpty()) {
                    listener.onSuccess(result)
                } else {
                    listener.onFail()
                }
            } catch (e: Exception) {
                listener.onFail()
            }
        }

        // Define similar functions for other compression levels
    }

    interface CompressListener {
        fun onStart()
        fun onSuccess(compressVideoPath: String)
        fun onFail()
        fun onProgress(percent: Float)
    }
}
