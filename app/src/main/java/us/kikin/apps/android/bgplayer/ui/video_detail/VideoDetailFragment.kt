package us.kikin.apps.android.bgplayer.ui.video_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import us.kikin.apps.android.bgplayer.databinding.FragmentVideoDetailBinding
import us.kikin.apps.android.bgplayer.models.VideoModel

@AndroidEntryPoint
class VideoDetailFragment : Fragment() {

    private var _binding: FragmentVideoDetailBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: VideoDetailViewModel by viewModels()

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

        viewModel.videoLiveData.observe(viewLifecycleOwner, { bindVideo(it) })
        viewModel.player.observe(viewLifecycleOwner, { binding.videoView.player = it })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun bindVideo(video: VideoModel) {
        binding.video = video
        binding.videoThumbnail.load(video.thumbnailUrl) { crossfade(true) }
    }
}
