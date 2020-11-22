package us.kikin.apps.android.bgplayer.network

import com.google.gson.annotations.SerializedName

class VideoResponse(
    @SerializedName("results") val videos: List<VideoDto>
)
