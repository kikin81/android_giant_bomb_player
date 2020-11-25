package us.kikin.apps.android.bgplayer.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShowDao {

    @Query("SELECT * FROM shows")
    suspend fun getAllShows(): List<ShowEntity>

    @Query("SELECT * FROM shows WHERE id = (:showId)")
    suspend fun getShowById(showId: Long): ShowEntity

    @Query("DELETE FROM shows")
    suspend fun clearShows()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shows: List<ShowEntity>)

    @Delete
    suspend fun delete(show: ShowEntity)
}
