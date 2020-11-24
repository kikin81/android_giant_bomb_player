package us.kikin.apps.android.bgplayer.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import us.kikin.apps.android.bgplayer.network.VideoApiHelper
import us.kikin.apps.android.bgplayer.network.VideoApiHelperImpl

@Module
@InstallIn(ApplicationComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindVideoHelper(videoApiHelperImpl: VideoApiHelperImpl): VideoApiHelper
}