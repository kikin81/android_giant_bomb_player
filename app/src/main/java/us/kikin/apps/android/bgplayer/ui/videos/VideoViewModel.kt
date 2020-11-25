package us.kikin.apps.android.bgplayer.ui.videos

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.repository.VideoRepository

class VideoViewModel @ViewModelInject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    private var currentSearchResult: Flow<PagingData<VideoModel>>? = null

    fun getLatestVideos(): Flow<PagingData<VideoModel>> {
        val lastResult = currentSearchResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<VideoModel>> =
            repository.getLatestVideosStream().cachedIn(viewModelScope)
        currentSearchResult = newResult

        return newResult
    }
}
