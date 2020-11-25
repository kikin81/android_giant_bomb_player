package us.kikin.apps.android.bgplayer.data

import androidx.paging.PagingSource
import java.io.IOException
import retrofit2.HttpException
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.network.IN_SHOW_QUALIFIER
import us.kikin.apps.android.bgplayer.network.VideoService

class ShowPagingSource(
    private val showId: Long,
    private val videoService: VideoService
) : PagingSource<Int, VideoModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoModel> {
        val offset = params.key ?: PAGE_OFFSET

        return try {
            val response = videoService.fetchVideosForShow(
                filter = "$IN_SHOW_QUALIFIER$showId",
                offset = offset,
                limit = PAGE_SIZE
            )
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

    companion object {
        private const val PAGE_OFFSET = 0
        private const val PAGE_SIZE = 50
    }
}
