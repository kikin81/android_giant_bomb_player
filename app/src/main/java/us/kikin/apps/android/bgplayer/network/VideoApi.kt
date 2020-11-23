package us.kikin.apps.android.bgplayer.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoApi {

    @GET("videos")
    suspend fun fetchVideos(): VideoResponse

    @GET("video/{video_id}")
    suspend fun fetchVideoById(@Path("video_id") id: Long): VideoDetailResponse

    @GET("videos")
    suspend fun fetchVideosForShow(
        @Query("filter") filter: String
    ): VideoResponse

    @GET("video_show/{show_id}")
    suspend fun fetchShow(@Path("show_id") id: Long): ShowResponse
}
