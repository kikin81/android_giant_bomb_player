package us.kikin.apps.android.bgplayer.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val IN_SHOW_QUALIFIER = "video_show:"

/**
 * Giantbomb API communication setup with retrofit
 */
interface VideoService {

    /**
     * Get latest videos
     */
    @GET("videos")
    suspend fun fetchVideos(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): VideoResponse

    @GET("video/{video_id}")
    suspend fun fetchVideoById(@Path("video_id") id: Long): VideoDetailResponse

    @GET("videos")
    suspend fun fetchVideosForShow(
        @Query("filter") filter: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): VideoResponse

    @GET("video_show/{show_id}")
    suspend fun fetchShow(@Path("show_id") id: Long): ShowResponse
}
