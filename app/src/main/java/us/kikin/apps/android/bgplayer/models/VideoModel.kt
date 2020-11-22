package us.kikin.apps.android.bgplayer.models

import android.text.format.DateUtils
import java.util.Date
import us.kikin.apps.android.bgplayer.network.VideoDto

data class VideoModel(
    val id: Long,
    val name: String,
    val length: Long,
    val thumbnailUrl: String,
    val description: String,
    val publishedDate: Date
) {
    constructor(dto: VideoDto) :
        this(
            dto.id,
            dto.name,
            dto.length,
            dto.imageDto.screenUrl,
            dto.description,
            dto.publishedDate
        )

    val publishedRelativeDay: String by lazy {
        val now = System.currentTimeMillis()
        DateUtils.getRelativeTimeSpanString(
            publishedDate.time,
            now,
            DateUtils.MINUTE_IN_MILLIS
        ).toString()
    }

    val runtimeDisplay: String by lazy {
        val hours = length / 3600
        val minutes = (length % 3600) / 60
        val seconds = length % 60

        if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }
}
