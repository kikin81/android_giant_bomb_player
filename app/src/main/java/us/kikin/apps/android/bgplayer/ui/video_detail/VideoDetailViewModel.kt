package us.kikin.apps.android.bgplayer.ui.video_detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.repository.VideoRepository

class VideoDetailViewModel @ViewModelInject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {

    val videoLiveData = MutableLiveData<VideoModel>()

    fun getVideoById(videoId: Long) {
        viewModelScope.launch {
            val response = videoRepository.getVideoById(videoId)
            videoLiveData.postValue(response)
        }
    }
}
