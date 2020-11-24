package us.kikin.apps.android.bgplayer.ui.show

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import us.kikin.apps.android.bgplayer.models.ShowModel
import us.kikin.apps.android.bgplayer.repository.VideoRepository

class ShowViewModel @ViewModelInject constructor(
    private val videoRepository: VideoRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    // TODO: https://issuetracker.google.com/issues/136967621
    private val showId: Long = savedStateHandle["showId"]
        ?: throw IllegalArgumentException("Show ID required")
    val videoListLiveData = MutableLiveData<ShowDataHolder>()

    init {
        // TODO: Use MediatorLiveData to combine multiple live data objects
        viewModelScope.launch {
            val videos = videoRepository.getVideosForShow(showId)
            val show = videoRepository.getShow(showId).show
            val foo = ShowDataHolder(videos, ShowModel(show))
            videoListLiveData.postValue(foo)
        }
    }
}
