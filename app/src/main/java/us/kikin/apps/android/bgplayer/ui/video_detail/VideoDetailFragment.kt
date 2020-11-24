package us.kikin.apps.android.bgplayer.ui.video_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import us.kikin.apps.android.bgplayer.databinding.FragmentVideoDetailBinding
import us.kikin.apps.android.bgplayer.models.VideoModel

@AndroidEntryPoint
class VideoDetailFragment : Fragment() {

    private var _binding: FragmentVideoDetailBinding? = null
    private val binding get() = requireNotNull(_binding) {
        throw IllegalStateException("Cannot access binding")
    }
    private val viewModel: VideoDetailViewModel by viewModels()
    private val args: VideoDetailFragmentArgs by navArgs()

    // player
    private lateinit var player: SimpleExoPlayer
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getVideoById(args.videoId)
        viewModel.videoLiveData.observe(
            viewLifecycleOwner,
            {
                bindVideo(it)
            }
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun bindVideo(video: VideoModel) {
        binding.videoTitle.text = video.name
        binding.videoDescription.text = video.description
        binding.videoPublishedDate.text = video.publishedRelativeDay
        binding.videoThumbnail.load(video.thumbnailUrl) { crossfade(true) }
        initializePlayer(video)
    }

    private fun initializePlayer(video: VideoModel) {
        player = SimpleExoPlayer.Builder(requireContext()).build()
        binding.videoView.player = player
        val mediaItem = MediaItem.fromUri(video.videoUri)
        player.setMediaItem(mediaItem)
        player.playWhenReady = playWhenReady
        player.seekTo(currentWindow, playbackPosition)
        player.prepare()
    }

    private fun releasePlayer() {
        playWhenReady = player.playWhenReady
        playbackPosition = player.currentPosition
        currentWindow = player.currentWindowIndex
        player.release()
    }
}
