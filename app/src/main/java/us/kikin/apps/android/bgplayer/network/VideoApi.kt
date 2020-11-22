package us.kikin.apps.android.bgplayer.network

import retrofit2.http.GET
import retrofit2.http.Path

interface VideoApi {

    @GET("videos")
    suspend fun fetchVideos(): VideoResponse

    @GET("video/{video_id}")
    suspend fun fetchVideoById(@Path("video_id") id: Long): VideoDetailResponse
}
