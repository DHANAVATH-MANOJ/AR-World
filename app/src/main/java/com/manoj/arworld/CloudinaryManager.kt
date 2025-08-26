package com.manoj.arworld.api

import android.content.Context
import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

object CloudinaryManager {

    private var initialized = false

    fun init(context: Context) {
        if (!initialized) {
            val config: MutableMap<String, String> = HashMap()
            config["cloud_name"] = "dooyclffc"
            config["api_key"] = "337524515658674"
            config["api_secret"] = "KpdbtizPtQM2EUKQebbaCu5vbSM"
            MediaManager.init(context, config)
            initialized = true
        }
    }

    fun uploadImage(context: Context, uri: Uri, callback: (String?) -> Unit) {
        MediaManager.get().upload(uri).callback(object : UploadCallback {
            override fun onStart(requestId: String?) {}
            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}
            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                val url = resultData?.get("secure_url") as? String
                callback(url)
            }
            override fun onError(requestId: String?, error: ErrorInfo?) {
                callback(null)
            }
            override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
        }).dispatch()
    }
}
