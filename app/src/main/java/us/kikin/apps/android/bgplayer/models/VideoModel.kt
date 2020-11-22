package us.kikin.apps.android.bgplayer.models

data class VideoModel(
    val id: Long,
    val name: String,
    val length: Int,
    val thumbnailUrl: String,
    val description: String
)
