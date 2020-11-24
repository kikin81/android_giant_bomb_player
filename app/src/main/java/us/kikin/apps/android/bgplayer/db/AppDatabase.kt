package us.kikin.apps.android.bgplayer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        VideoEntity::class,
        ShowEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao

    abstract fun showDao(): ShowDao
}
