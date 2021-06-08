package us.kikin.apps.android.bgplayer.ui.video_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.load
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import dagger.hilt.android.AndroidEntryPoint
import us.kikin.apps.android.bgplayer.R
import us.kikin.apps.android.bgplayer.databinding.FragmentVideoDetailBinding
import us.kikin.apps.android.bgplayer.models.VideoModel

@AndroidEntryPoint
class VideoDetailFragment : Fragment(), Player.EventListener {

    private var _binding: FragmentVideoDetailBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: VideoDetailViewModel by viewModels()
    private lateinit var closeButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoDetailBinding.inflate(inflater, container, false)
        // part of player controls, can't use view binding
        closeButton = binding.root.findViewById(R.id.close_button)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener {
            it.findNavController().popBackStack()
        }
        viewModel.videoLiveData.observe(viewLifecycleOwner, { bindVideo(it) })
        viewModel.player.observe(
            viewLifecycleOwner,
            {
                binding.videoView.player = it
                binding.videoView.player?.addListener(this)
            }
        )
    }

    override fun onPlaybackStateChanged(state: Int) {
        super.onPlaybackStateChanged(state)
        when (state) {
            ExoPlayer.STATE_IDLE -> Log.d("exoplayer", "ExoPlayer.STATE_IDLE")
            ExoPlayer.STATE_BUFFERING -> {
                binding.videoThumbnail.isVisible = true
                binding.loadingSpinner.isVisible = true
            }
            ExoPlayer.STATE_READY -> {
                binding.loadingSpinner.isVisible = false
                binding.videoThumbnail.animate().alpha(0.0f)
                Log.d("exoplayer", "ExoPlayer.STATE_READY")
            }
            ExoPlayer.STATE_ENDED -> Log.d("exoplayer", "ExoPlayer.STATE_ENDED")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun bindVideo(video: VideoModel) {
        binding.video = video
        binding.videoThumbnail.load(video.thumbnailUrl)
    }
}
