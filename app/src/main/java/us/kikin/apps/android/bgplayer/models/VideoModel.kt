package us.kikin.apps.android.bgplayer.models

import android.net.Uri
import android.text.format.DateUtils
import java.util.Date
import us.kikin.apps.android.bgplayer.BuildConfig
import us.kikin.apps.android.bgplayer.network.VideoDto

data class VideoModel(
    val id: Long,
    val name: String,
    val durationSeconds: Long,
    val thumbnailUrl: String,
    val description: String,
    val publishedDate: Date,
    val showModel: ShowModel?,
    val hdUrl: String,
    val user: String?
) {
    constructor(dto: VideoDto) :
        this(
            dto.id,
            dto.name,
            dto.durationSeconds,
            dto.imageDto.screenUrl,
            dto.description,
            dto.publishedDate,
            if (dto.videoShow != null) {
                ShowModel(dto.videoShow)
            } else {
                null
            },
            dto.hdUrl ?: (dto.highUrl ?: dto.lowUrl),
            dto.user
        )

    val publishedInfoDisplay: String by lazy {
        val now = System.currentTimeMillis()
        val relativeDate = DateUtils.getRelativeTimeSpanString(
            publishedDate.time,
            now,
            DateUtils.MINUTE_IN_MILLIS
        )
        if (user != null) {
            "$relativeDate by $user"
        } else {
            relativeDate.toString()
        }
    }

    val runtimeDisplay: String by lazy {
        val hours = durationSeconds / 3600
        val minutes = (durationSeconds % 3600) / 60
        val seconds = durationSeconds % 60

        if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }

    val videoUri: Uri by lazy {
        val url = "$hdUrl?api_key=${BuildConfig.ApiKey}"
        Uri.parse(url)
    }
}
