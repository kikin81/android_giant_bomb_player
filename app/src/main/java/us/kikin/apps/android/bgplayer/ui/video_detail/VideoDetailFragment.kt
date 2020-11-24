package us.kikin.apps.android.bgplayer.ui.video_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import us.kikin.apps.android.bgplayer.databinding.FragmentVideoDetailBinding
import us.kikin.apps.android.bgplayer.models.VideoModel

@AndroidEntryPoint
class VideoDetailFragment : Fragment() {

    private var _binding: FragmentVideoDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VideoDetailViewModel by viewModels()
    private val args: VideoDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun bindVideo(video: VideoModel) {
        binding.videoTitle.text = video.name
        binding.videoDescription.text = video.description
        binding.videoThumbnail.load(video.thumbnailUrl) {
            crossfade(true)
        }
    }
}
