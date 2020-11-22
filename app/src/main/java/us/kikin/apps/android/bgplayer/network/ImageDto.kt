package us.kikin.apps.android.bgplayer.network

import com.google.gson.annotations.SerializedName

class ImageDto(
    @SerializedName("icon_url") val iconUrl: String,
    @SerializedName("thumb_url") val thumbUrl: String
)
