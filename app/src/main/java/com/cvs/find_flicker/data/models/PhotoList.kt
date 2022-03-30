package com.cvs.find_flicker.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoList(
    val description: String,
    val generator: String,
    val items: List<Photo>,
    val link: String,
    val modified: String,
    val title: String
): Parcelable