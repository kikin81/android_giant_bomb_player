package us.kikin.apps.android.bgplayer.network

import com.google.gson.annotations.SerializedName

class VideoShowDto(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val name: String,
    @SerializedName("image") val imageDto: ImageDto
)
