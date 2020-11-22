package us.kikin.apps.android.bgplayer.repository

import us.kikin.apps.android.bgplayer.network.VideoApi
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val videoApi: VideoApi
) {
    suspend fun getVideos() = videoApi.fetchVideos()
}
