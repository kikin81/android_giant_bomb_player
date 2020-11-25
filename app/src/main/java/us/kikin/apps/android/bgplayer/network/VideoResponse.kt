package us.kikin.apps.android.bgplayer.network

import com.google.gson.annotations.SerializedName

class VideoResponse(
    @SerializedName("offset") val offset: Int,
    @SerializedName("number_of_total_results") val totalResults: Int,
    @SerializedName("results") val videos: List<VideoDto>
)
