package us.kikin.apps.android.bgplayer.ui.show

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import us.kikin.apps.android.bgplayer.models.ShowModel
import us.kikin.apps.android.bgplayer.repository.VideoRepository

class ShowViewModel @ViewModelInject constructor(
    private val repository: VideoRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    // TODO: https://issuetracker.google.com/issues/136967621
    private val showId: Long = savedStateHandle["showId"]
        ?: throw IllegalArgumentException("Show ID required")
    private var currentShowResult: Flow<PagingData<ShowUiModel>>? = null
    val showLiveData = MutableLiveData<ShowModel>()

    init {
        viewModelScope.launch {
            val response = repository.getShow(showId)
            showLiveData.postValue(ShowModel(response.show))
        }
    }

    fun getVideosForShow(showModel: ShowModel): Flow<PagingData<ShowUiModel>> {
        val lastResults = currentShowResult
        if (lastResults != null) {
            return lastResults
        }

        val newResult: Flow<PagingData<ShowUiModel>> = repository.getVideosForShowStream(showId)
            .map { paging -> paging.map { ShowUiModel.VideoUiModel(it) } }
            .map {
                it.insertSeparators { before, _ ->
                    if (before == null) {
                        return@insertSeparators ShowUiModel.Header(showModel)
                    } else {
                        return@insertSeparators null
                    }
                }
            }.cachedIn(viewModelScope)
        currentShowResult = newResult
        return newResult
    }
}
