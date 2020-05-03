package com.cmoney.demo.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
class CellInfo(
    var albumId: Int,
    var id: Int,
    var title: String ? = null,
    var url: String,
    var thumbnailUrl: String
) : Parcelable {

    fun getConvertedThumbnailUrl() : String{
        return convertUrl(thumbnailUrl)
    }

    fun getConvertedUrl() : String{
        return convertUrl(url)
    }

    fun convertUrl(url : String) : String {
        var convertedUrl = url!!.replace("https://via.placeholder.com", "https://ipsumimage.appspot.com/")
        return convertedUrl.substring(0, convertedUrl.length - 7) + "," + convertedUrl.substring(convertedUrl.length - 6)
    }
}

