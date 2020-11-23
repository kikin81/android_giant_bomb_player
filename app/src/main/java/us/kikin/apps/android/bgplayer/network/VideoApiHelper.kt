package us.kikin.apps.android.bgplayer.network

interface VideoApiHelper {
    suspend fun getVideos(): VideoResponse

    suspend fun getVideoById(videoId: Long): VideoDetailResponse

    suspend fun getVideosForShow(showId: Long): VideoResponse

    suspend fun getShow(showId: Long): ShowResponse
}
