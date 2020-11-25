package us.kikin.apps.android.bgplayer.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import us.kikin.apps.android.bgplayer.data.VideoPagingSource
import us.kikin.apps.android.bgplayer.db.VideoDao
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.network.IN_SHOW_QUALIFIER
import us.kikin.apps.android.bgplayer.network.VideoService

@Singleton
class VideoRepository @Inject constructor(
    private val videoService: VideoService,
    private val videoDao: VideoDao
) {

    fun getLatestVideosStream(): Flow<PagingData<VideoModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { VideoPagingSource(videoService) }
        ).flow
    }

    suspend fun getVideoById(videoId: Long): VideoModel {
        val response = videoService.fetchVideoById(videoId).video
        return VideoModel(response)
    }

    suspend fun getVideosForShow(showId: Long) =
        videoService.fetchVideosForShow("$IN_SHOW_QUALIFIER$showId")
            .videos.map { VideoModel(it) }

    suspend fun getShow(showId: Long) =
        videoService.fetchShow(showId)

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}
