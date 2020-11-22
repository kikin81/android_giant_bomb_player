package us.kikin.apps.android.bgplayer.ui.videos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.repository.VideoRepository
import javax.inject.Inject

class VideoViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {

    val videoLiveData = MutableLiveData<List<VideoModel>>()

    init {
        viewModelScope.launch {
            val response = videoRepository.getVideos()
            videoLiveData.postValue(response)
        }
    }
}