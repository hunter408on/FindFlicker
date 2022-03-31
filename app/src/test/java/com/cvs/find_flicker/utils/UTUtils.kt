package com.cvs.find_flicker.utils

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.cvs.find_flicker.data.models.PhotoList
import com.google.gson.Gson
import java.io.InputStream

object UTUtils {
    fun getJson(path: String): String? {
        val inputStream: InputStream? = javaClass.classLoader?.getResourceAsStream(path)
        return inputStream?.bufferedReader().use { it?.readText() }
    }

    fun anyString() = "anyString"
    fun anyOtherString() = "anyOtherString"
    fun anyListString() = listOf(anyString(), anyOtherString())
    fun getAnyPhotoList(): PhotoList {
        var photoList: PhotoList? = null
        val jsonText = UTUtils.getJson("PhotosApiResponse.json")
        jsonText?.let {
            photoList = Gson().fromJson(it, PhotoList::class.java)
        }
        if (photoList == null) {
            assert(false)
        }
        return photoList!!
    }
}