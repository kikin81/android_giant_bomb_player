package us.kikin.apps.android.bgplayer.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

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
)
