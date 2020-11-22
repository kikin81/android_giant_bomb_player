package us.kikin.apps.android.bgplayer.repository

import javax.inject.Inject
import javax.inject.Singleton
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.network.VideoApiHelper

@Singleton
class VideoRepository @Inject constructor(
    private val videoApiHelper: VideoApiHelper
) {
    suspend fun getVideos() =
        videoApiHelper.getVideos().videos.map {
            VideoModel(
                it.id,
                it.name,
                it.length,
                it.imageDto.screenUrl,
                it.description
            )
        }

    suspend fun getVideoById(videoId: Long): VideoModel {
        val response = videoApiHelper.getVideoById(videoId).video
        return VideoModel(
            response.id,
            response.name,
            response.length,
            response.imageDto.screenUrl,
            response.description
        )
    }
}
