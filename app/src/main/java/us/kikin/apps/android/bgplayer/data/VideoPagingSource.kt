package us.kikin.apps.android.bgplayer.data

import androidx.paging.PagingSource
import java.io.IOException
import retrofit2.HttpException
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.network.VideoService

private const val NEXT_PAGE_INDEX = 1

class VideoPagingSource(
    private val videoService: VideoService,
    private val query: String
) : PagingSource<Int, VideoModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoModel> {
        val position = params.key ?: NEXT_PAGE_INDEX

        return try {
            val response = videoService.fetchVideos(0, 50)
            val videos = response.videos.map { VideoModel(it) }
            LoadResult.Page(
                data = videos,
                prevKey = if (position == NEXT_PAGE_INDEX) null else position - 1,
                nextKey = if (videos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
