package us.kikin.apps.android.bgplayer.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import us.kikin.apps.android.bgplayer.data.ShowPagingSource
import us.kikin.apps.android.bgplayer.data.VideoPagingSource
import us.kikin.apps.android.bgplayer.data.VideoSearchPagingSource
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.network.VideoService

@Singleton
class VideoRepository @Inject constructor(
    private val videoService: VideoService,
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

    fun getVideosForQueryStream(query: String): Flow<PagingData<VideoModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { VideoSearchPagingSource(videoService, query) }
        ).flow
    }

    fun getVideosForShowStream(showId: Long): Flow<PagingData<VideoModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ShowPagingSource(showId, videoService) }
        ).flow
    }

    suspend fun getVideoById(videoId: Long): VideoModel {
        val response = videoService.fetchVideoById(videoId).video
        return VideoModel(response)
    }

    suspend fun getShow(showId: Long) =
        videoService.fetchShow(showId)

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}
