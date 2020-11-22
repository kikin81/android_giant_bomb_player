package us.kikin.apps.android.bgplayer.network

import com.google.gson.annotations.SerializedName

class VideoDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("length_seconds") val length: Int,
    @SerializedName("image") val imageDto: ImageDto
)
