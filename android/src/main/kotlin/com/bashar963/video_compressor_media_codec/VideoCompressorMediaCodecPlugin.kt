package com.bashar963.video_compressor_media_codec


import com.bashar963.video_compressor_media_codec.videocompressor.VideoCompress
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result



/** VideoCompressorMediaCodecPlugin */
class VideoCompressorMediaCodecPlugin: FlutterPlugin, MethodCallHandler, EventChannel.StreamHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var eventChannel : EventChannel
  private var mEventSink: EventChannel.EventSink? = null


  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "video_compressor_media_codec")
    channel.setMethodCallHandler(this)
    eventChannel = EventChannel(flutterPluginBinding.binaryMessenger, "video_compressor_media_codec/compressVideoProgress")
    eventChannel.setStreamHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {

    if (call.method == "compressVideo") {

      val compressType = call.argument<String>("compressType")
      val filePath = call.argument<String>("filePath")
      val outputFilePath = call.argument<String>("outputFilePath")

      if(filePath==null || outputFilePath==null){
            result.error("File Path Error", "File Path Error", "File Path Error")
      }




      when (compressType) {
          "high" -> {
            highCompress(filePath!!,outputFilePath!!, result)
          }
          "medium" -> {
            mediumCompress(filePath!!,outputFilePath!!, result)
          }
          "low" -> {
            lowCompress(filePath!!,outputFilePath!!, result)
          }
          else -> {
              result.error("Compress Type Error", "Compress Type Error", "Compress Type Error")
          }
      }
    } else {
      result.notImplemented()
    }
  }

  private fun highCompress(filePath: String, outputFilePath:String, result: Result) {

      VideoCompress.compressVideoHigh(filePath, outputFilePath, object : VideoCompress.CompressListener {
        override fun onStart() {}
        override fun onSuccess(compressVideoPath: String) {
          result.success(compressVideoPath)
        }
        override fun onFail() {
          result.error("Compress Error", "Compress Error", "Compress Error")
        }
        override fun onProgress(percent: Float) {
          mEventSink?.success(percent)
        }
      }
      )

  }

  private fun mediumCompress(filePath: String,outputFilePath:String, result: Result) {

      VideoCompress.compressVideoMedium(filePath, outputFilePath, object : VideoCompress.CompressListener {
        override fun onStart() {}
        override fun onSuccess(compressVideoPath: String) {
          result.success(compressVideoPath)
        }
        override fun onFail() {
          result.error("Compress Error", "Compress Error", "Compress Error")
        }
        override fun onProgress(percent: Float) {

          mEventSink?.success(percent)
        }
      }
      )

  }

  private fun lowCompress(filePath: String,outputFilePath:String, result: Result) {

      VideoCompress.compressVideoLow(filePath, outputFilePath, object : VideoCompress.CompressListener {
        override fun onStart() {}
        override fun onSuccess(compressVideoPath: String) {
          result.success(compressVideoPath)
        }
        override fun onFail() {
          result.error("Compress Error", "Compress Error", "Compress Error")
        }
        override fun onProgress(percent: Float) {
          mEventSink?.success(percent)
        }
      }
      )

  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
    eventChannel.setStreamHandler(null)
  }

  override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
    mEventSink = events
  }

  override fun onCancel(arguments: Any?) {
    mEventSink = null
  }
}
