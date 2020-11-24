package us.kikin.apps.android.bgplayer.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import us.kikin.apps.android.bgplayer.network.VideoApiHelper
import us.kikin.apps.android.bgplayer.network.VideoApiHelperImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindVideoHelper(videoApiHelperImpl: VideoApiHelperImpl): VideoApiHelper
}
