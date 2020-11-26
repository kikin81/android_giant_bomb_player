package us.kikin.apps.android.bgplayer.ui.video_detail

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.launch
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.repository.VideoRepository

class VideoDetailViewModel @ViewModelInject constructor(
    application: Application,
    private val videoRepository: VideoRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : AndroidViewModel(application), LifecycleObserver {

    // TODO: https://issuetracker.google.com/issues/136967621
    private val videoId: Long = savedStateHandle["videoId"]
        ?: throw IllegalArgumentException("videoId required")
    private val _video = MutableLiveData<VideoModel>()
    val videoLiveData: LiveData<VideoModel> get() = _video
    // player
    private val _player = MutableLiveData<Player?>()
    val player: LiveData<Player?> get() = _player
    private var playbackPosition: Long = 0L
    private var playWhenReady = true

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        getVideoById(videoId)
    }

    private fun getVideoById(videoId: Long) {
        viewModelScope.launch {
            val response = videoRepository.getVideoById(videoId)
            setupPlayer(response)
            _video.postValue(response)
        }
    }

    // player lifecycle events
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForegrounded() {
        val video = videoLiveData.value ?: return
        setupPlayer(video)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackgrounded() {
        releaseExoPlayer()
    }

    private fun setupPlayer(video: VideoModel) {
        val player = SimpleExoPlayer.Builder(getApplication()).build()
        val mediaItem = MediaItem.fromUri(video.videoUri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = playWhenReady
        player.seekTo(playbackPosition)
        this._player.value = player
    }

    private fun releaseExoPlayer() {
        val player = _player.value ?: return
        this._player.value = null

        playbackPosition = player.contentPosition
        playWhenReady = player.playWhenReady
        player.release()
    }

    override fun onCleared() {
        super.onCleared()
        releaseExoPlayer()
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }
}
