package us.kikin.apps.android.bgplayer.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoApiHelperImpl @Inject constructor(
    private val api: VideoApi
) : VideoApiHelper {
    override suspend fun getVideos() = api.fetchVideos()
}
