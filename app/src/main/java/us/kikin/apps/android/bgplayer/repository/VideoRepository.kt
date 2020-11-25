package us.kikin.apps.android.bgplayer.repository

import javax.inject.Inject
import javax.inject.Singleton
import us.kikin.apps.android.bgplayer.db.VideoDao
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.network.IN_SHOW_QUALIFIER
import us.kikin.apps.android.bgplayer.network.VideoService

@Singleton
class VideoRepository @Inject constructor(
    private val videoService: VideoService,
    private val videoDao: VideoDao
) {
    suspend fun getVideos() =
        videoService.fetchVideos(0, 50).videos.map { VideoModel(it) }

    suspend fun getVideoById(videoId: Long): VideoModel {
        val response = videoService.fetchVideoById(videoId).video
        return VideoModel(response)
    }

    suspend fun getVideosForShow(showId: Long) =
        videoService.fetchVideosForShow("$IN_SHOW_QUALIFIER$showId")
            .videos.map { VideoModel(it) }

    suspend fun getShow(showId: Long) =
        videoService.fetchShow(showId)
}
