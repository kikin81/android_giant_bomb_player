package us.kikin.apps.android.bgplayer.network

import javax.inject.Inject

class VideoApiHelperImpl @Inject constructor(
    private val videoService: VideoService
) : VideoApiHelper {
    override suspend fun getVideos() = videoService.fetchVideos()

    override suspend fun getVideoById(videoId: Long) =
        videoService.fetchVideoById(videoId)

    override suspend fun getVideosForShow(showId: Long) =
        videoService.fetchVideosForShow("video_show:$showId")

    override suspend fun getShow(showId: Long): ShowResponse =
        videoService.fetchShow(showId)
}
