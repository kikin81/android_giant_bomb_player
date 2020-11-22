package us.kikin.apps.android.bgplayer.network

interface VideoApiHelper {
    suspend fun getVideos(): VideoResponse

    suspend fun getVideoById(videoId: Long): VideoDetailResponse
}
