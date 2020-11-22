package us.kikin.apps.android.bgplayer.models

import android.text.format.DateUtils
import us.kikin.apps.android.bgplayer.network.VideoDto
import java.util.Date

class VideoModel(
    val id: Long,
    val name: String,
    val length: Int,
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

    fun publishDateDisplay(): String {
        val now = System.currentTimeMillis();
        return DateUtils.getRelativeTimeSpanString(
            publishedDate.time,
            now,
            DateUtils.MINUTE_IN_MILLIS).toString()
    }
}
