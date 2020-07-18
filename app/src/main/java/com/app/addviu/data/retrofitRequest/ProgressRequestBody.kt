package com.app.addviu.data.retrofitRequest

import android.os.Handler
import android.os.Looper
import com.app.addviu.view.viewInterface.UploadCallback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream


class ProgressRequestBody(
    private val file: File, private val contentType: String,
    val uploadCallback: UploadCallback
) : RequestBody() {


    private val defaultBufferSize = 2048


    override fun contentLength(): Long {
        return file.length()
    }

    override fun contentType(): MediaType? {
        return "$contentType/*".toMediaTypeOrNull()
    }

    override fun writeTo(sink: BufferedSink) {
        val fileLength: Long = file.length()
        val buffer = ByteArray(defaultBufferSize)
        val `in` = FileInputStream(file)
        var uploaded: Long = 0

        `in`.use { inn ->
            var read: Int
            val handler = Handler(Looper.getMainLooper())
            while (inn.read(buffer).also { read = it } != -1) {

                // update progress on UI thread
                handler.post(ProgressUpdater(uploaded, fileLength))
                uploaded += read.toLong()
                sink.write(buffer, 0, read)
            }
        }
    }

    inner class ProgressUpdater(private val uploaded: Long, private val total: Long) : Runnable {
        override fun run() {
            uploadCallback.onProgressUpdate((100 * uploaded / total).toInt())
        }

    }


}