package us.kikin.apps.android.bgplayer.network

import com.google.gson.annotations.SerializedName
import java.util.Date

class VideoDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("length_seconds") val durationSeconds: Long,
    @SerializedName("image") val imageDto: ImageDto,
    @SerializedName("deck") val description: String,
    @SerializedName("publish_date") val publishedDate: Date,
    @SerializedName("video_show") val videoShow: VideoShowDto?,
    @SerializedName("hd_url") val hdUrl: String,
    @SerializedName("user") val user: String?
)
