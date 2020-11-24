package us.kikin.apps.android.bgplayer.db

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Provides
    fun provideVideoDao(appDatabase: AppDatabase): VideoDao {
        return appDatabase.videoDao()
    }

    @Provides
    fun provideShowDao(appDatabase: AppDatabase): ShowDao {
        return appDatabase.showDao()
    }
}
