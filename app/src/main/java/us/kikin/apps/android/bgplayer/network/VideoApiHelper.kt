package us.kikin.apps.android.bgplayer.network

interface VideoApiHelper {
    suspend fun getVideos(): VideoResponse
}
