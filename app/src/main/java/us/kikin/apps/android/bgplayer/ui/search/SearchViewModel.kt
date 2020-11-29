package us.kikin.apps.android.bgplayer.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.repository.VideoRepository

class SearchViewModel @ViewModelInject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    private var debouncePeriod: Long = 500
    private var searchJob: Job? = null
    private val _searchFieldTextLiveData = MutableLiveData<String>()
    private var currentSearchResults: Flow<PagingData<VideoModel>>? = null

    fun onSearchQuery(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(debouncePeriod)
            if (query.length > 3) {
                getVideosWithQuery(query)
            }
        }
    }

    fun getVideosWithQuery(query: String): Flow<PagingData<VideoModel>> {
        val lastResult = currentSearchResults
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<VideoModel>> =
            repository.getVideosForQueryStream(query).cachedIn(viewModelScope)
        currentSearchResults = newResult
        return newResult
    }
}
