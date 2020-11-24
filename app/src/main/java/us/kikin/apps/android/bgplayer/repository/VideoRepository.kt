package us.kikin.apps.android.bgplayer.repository

import javax.inject.Inject
import javax.inject.Singleton
import us.kikin.apps.android.bgplayer.db.VideoDao
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.network.VideoApiHelper

@Singleton
class VideoRepository @Inject constructor(
    private val videoApiHelper: VideoApiHelper,
    private val videoDao: VideoDao
) {
    suspend fun getVideos() =
        videoApiHelper.getVideos().videos.map { VideoModel(it) }

    suspend fun getVideoById(videoId: Long): VideoModel {
        val response = videoApiHelper.getVideoById(videoId).video
        return VideoModel(response)
    }

    suspend fun getVideosForShow(showId: Long) =
        videoApiHelper.getVideosForShow(showId).videos.map { VideoModel(it) }

    suspend fun getShow(showId: Long) =
        videoApiHelper.getShow(showId)
}
