package com.bashar963.video_compressor_media_codec

import androidx.annotation.NonNull
import com.bashar963.video_compressor_media_codec.videocompressor.VideoCompress
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** VideoCompressorMediaCodecPlugin */
class VideoCompressorMediaCodecPlugin : FlutterPlugin, MethodCallHandler, EventChannel.StreamHandler {

    private lateinit var channel: MethodChannel
    private lateinit var eventChannel: EventChannel
    private var eventSink: EventChannel.EventSink? = null
    private var videoCompressTask: VideoCompress.VideoCompressTask? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "video_compressor_media_codec")
        channel.setMethodCallHandler(this)

        eventChannel = EventChannel(
            flutterPluginBinding.binaryMessenger,
            "video_compressor_media_codec/compressVideoProgress"
        )
        eventChannel.setStreamHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "compressVideo" -> {
                val compressType = call.argument<String>("compressType")
                val filePath = call.argument<String>("filePath")
                val outputFilePath = call.argument<String>("outputFilePath")

                if (filePath.isNullOrEmpty() || outputFilePath.isNullOrEmpty()) {
                    result.error("FILE_PATH_ERROR", "File path is null or empty", null)
                    return
                }

                when (compressType) {
                    "high" -> compressVideo(filePath, outputFilePath, result, CompressLevel.HIGH)
                    "medium" -> compressVideo(filePath, outputFilePath, result, CompressLevel.MEDIUM)
                    "low" -> compressVideo(filePath, outputFilePath, result, CompressLevel.LOW)
                    else -> result.error("COMPRESS_TYPE_ERROR", "Invalid compression type", null)
                }
            }

            "cancelVideoCompressTask" -> {
                val canceled = videoCompressTask?.cancel(true) ?: false
                result.success(canceled)
            }

            else -> result.notImplemented()
        }
    }

    private fun compressVideo(
        filePath: String,
        outputFilePath: String,
        result: Result,
        level: CompressLevel
    ) {
        val listener = object : VideoCompress.CompressListener {
            override fun onStart() {}

            override fun onSuccess(compressVideoPath: String) {
                // Ensure result is returned only once
                result.success(compressVideoPath)
                videoCompressTask = null
            }

            override fun onFail() {
                result.error("COMPRESS_ERROR", "Video compression failed", null)
                videoCompressTask = null
            }

            override fun onProgress(percent: Float) {
                eventSink?.success(percent)
            }
        }

        videoCompressTask = when (level) {
            CompressLevel.HIGH -> VideoCompress.compressVideoHigh(filePath, outputFilePath, listener)
            CompressLevel.MEDIUM -> VideoCompress.compressVideoMedium(filePath, outputFilePath, listener)
            CompressLevel.LOW -> VideoCompress.compressVideoLow(filePath, outputFilePath, listener)
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
        eventChannel.setStreamHandler(null)
        eventSink = null
        videoCompressTask = null
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        eventSink = events
    }

    override fun onCancel(arguments: Any?) {
        eventSink = null
    }

    private enum class CompressLevel {
        HIGH, MEDIUM, LOW
    }
}
