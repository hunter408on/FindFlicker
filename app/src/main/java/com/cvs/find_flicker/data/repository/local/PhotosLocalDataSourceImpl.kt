package com.cvs.find_flicker.data.repository.local

import android.content.SharedPreferences
import com.cvs.find_flicker.data.models.KEY_QUERIES
import com.google.gson.Gson
import javax.inject.Inject

class PhotosLocalDataSourceImpl @Inject constructor(private val sharedPref: SharedPreferences): PhotosLocalDataSource {
    override suspend fun fetchRecentQuery(): List<String> {
        val stringValue = sharedPref.getString(KEY_QUERIES, "")
        if (stringValue!!.isEmpty()) return emptyList()
        val queryList = Gson().fromJson(stringValue, Array<String>::class.java)
        return queryList.toList()
    }

    override suspend fun updateRecentQuery(queryList: List<String>) {
        val stringValue = Gson().toJson(queryList.toTypedArray())
        val editor = sharedPref.edit()
        editor.putString(KEY_QUERIES, stringValue)
        editor.apply()
    }
}