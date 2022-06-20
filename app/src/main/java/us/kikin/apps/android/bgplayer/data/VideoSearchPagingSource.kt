package us.kikin.apps.android.bgplayer.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException
import retrofit2.HttpException
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.network.VideoService

class VideoSearchPagingSource(
    private val videoService: VideoService,
    private val query: String
) : PagingSource<Int, VideoModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoModel> {
        val offset = params.key ?: PAGE_OFFSET

        return try {
            val response = videoService.fetchVideosForQuery(query, offset, PAGE_SIZE)
            val videos = response.videos.map { VideoModel(it) }
            LoadResult.Page(
                data = videos,
                prevKey = if (offset == PAGE_OFFSET) null else offset - response.offset,
                nextKey = if (videos.isEmpty()) null else offset + response.videos.size
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, VideoModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val PAGE_OFFSET = 0
        private const val PAGE_SIZE = 50
    }
}
