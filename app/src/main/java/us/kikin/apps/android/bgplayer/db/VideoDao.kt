package us.kikin.apps.android.bgplayer.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VideoDao {
    @Query("SELECT * FROM videos")
    fun getAllVideos(): List<VideoEntity>

    @Query("SELECT * FROM videos WHERE id = (:videoId)")
    fun getVideoById(videoId: Long): VideoEntity

    @Insert
    fun insertAll(vararg videos: VideoEntity)

    @Delete
    fun delete(video: VideoEntity)
}
