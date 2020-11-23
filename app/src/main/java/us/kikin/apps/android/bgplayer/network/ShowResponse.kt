package us.kikin.apps.android.bgplayer.network

import com.google.gson.annotations.SerializedName

class ShowResponse(
    @SerializedName("results") val show: VideoShowDto
)
