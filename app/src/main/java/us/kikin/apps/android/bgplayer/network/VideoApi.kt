package us.kikin.apps.android.bgplayer.network

import retrofit2.http.GET

interface VideoApi {

    @GET("videos")
    suspend fun fetchVideos(): VideoResponse
}
