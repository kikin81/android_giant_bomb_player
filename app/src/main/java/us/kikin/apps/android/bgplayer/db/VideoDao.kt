package us.kikin.apps.android.bgplayer.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao {
    @Query("SELECT * FROM videos")
    suspend fun getAllVideos(): List<VideoEntity>

    @Query("SELECT * FROM videos WHERE id = (:videoId)")
    suspend fun getVideoById(videoId: Long): VideoEntity

    @Query("DELETE FROM videos")
    suspend fun clearVideos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videos: List<VideoEntity>)

    @Delete
    suspend fun delete(video: VideoEntity)
}
