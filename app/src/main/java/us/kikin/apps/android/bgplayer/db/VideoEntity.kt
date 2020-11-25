package us.kikin.apps.android.bgplayer.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import us.kikin.apps.android.bgplayer.network.VideoDto

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "duration") val duration: Long,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "publish_date") val publishedDate: Date,
    @ColumnInfo(name = "hd_url") val hdUrl: String,
    @ColumnInfo(name = "show_id") val showId: Long?,
) {
    constructor(dto: VideoDto) : this(
        id = dto.id,
        name = dto.name,
        duration = dto.durationSeconds,
        thumbnailUrl = dto.imageDto.screenUrl,
        description = dto.description,
        publishedDate = dto.publishedDate,
        hdUrl = dto.hdUrl,
        showId = dto.videoShow?.id
    )
}
