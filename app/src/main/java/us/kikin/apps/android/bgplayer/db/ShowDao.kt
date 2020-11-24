package us.kikin.apps.android.bgplayer.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShowDao {

    @Query("SELECT * FROM shows")
    fun getAllShows(): List<ShowEntity>

    @Query("SELECT * FROM shows WHERE id = (:showId)")
    fun getShowById(showId: Long): ShowEntity

    @Insert
    fun insertAll(vararg shows: ShowEntity)

    @Delete
    fun delete(show: ShowEntity)
}
