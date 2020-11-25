package us.kikin.apps.android.bgplayer.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import java.io.IOException
import retrofit2.HttpException
import us.kikin.apps.android.bgplayer.db.AppDatabase
import us.kikin.apps.android.bgplayer.db.VideoEntity
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.network.VideoService

@OptIn(ExperimentalPagingApi::class)
class GiantBombRemoteMediator(
    private val service: VideoService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, VideoModel>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, VideoModel>
    ): MediatorResult {
        val offset: Int = when (loadType) {
            LoadType.REFRESH -> {
                0
            }
            LoadType.PREPEND -> {
                0
            }
            LoadType.APPEND -> {
                0
            }
        }

        try {
            val apiResponse = service.fetchVideos(offset, state.config.pageSize)
            val videos = apiResponse.videos
            val endOfPaginationReached = videos.isEmpty()

            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.videoDao().clearVideos()
                }
                val prevKey = if (offset == STARTING_OFFSET) null else offset - 1
                val nextKey = if (endOfPaginationReached) null else offset + 1
                appDatabase.videoDao().insertAll(videos.map { VideoEntity(it) })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_OFFSET = 0
        private const val PAGE_SIZE = 50
    }
}
