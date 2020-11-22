package us.kikin.apps.android.bgplayer.repository

import javax.inject.Inject
import us.kikin.apps.android.bgplayer.network.VideoApi

class VideoRepository @Inject constructor(
    private val videoApi: VideoApi
) {
    suspend fun getVideos() = videoApi.fetchVideos()
}
