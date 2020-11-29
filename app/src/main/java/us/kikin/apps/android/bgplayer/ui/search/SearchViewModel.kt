package us.kikin.apps.android.bgplayer.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.repository.VideoRepository

class SearchViewModel @ViewModelInject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    private var currentSearchResults: Flow<PagingData<VideoModel>>? = null
    private var currentQueryValue: String? = null

    fun getVideosWithQuery(query: String): Flow<PagingData<VideoModel>> {
        val lastResult = currentSearchResults
        if (query == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = query
        val newResult: Flow<PagingData<VideoModel>> =
            repository.getVideosForQueryStream(query).cachedIn(viewModelScope)
        currentSearchResults = newResult
        return newResult
    }
}
