package us.kikin.apps.android.bgplayer.network

import com.google.gson.annotations.SerializedName

class VideoDetailResponse(
    @SerializedName("results") val video: VideoDto
)
