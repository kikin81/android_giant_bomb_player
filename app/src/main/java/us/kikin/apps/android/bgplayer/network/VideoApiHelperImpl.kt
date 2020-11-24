package us.kikin.apps.android.bgplayer.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoApiHelperImpl @Inject constructor(
    private val api: VideoApi
) : VideoApiHelper {
    override suspend fun getVideos() = api.fetchVideos()

    override suspend fun getVideoById(videoId: Long) =
        api.fetchVideoById(videoId)

    override suspend fun getVideosForShow(showId: Long) =
        api.fetchVideosForShow("video_show:$showId")

    override suspend fun getShow(showId: Long): ShowResponse =
        api.fetchShow(showId)
}
