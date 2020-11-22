package us.kikin.apps.android.bgplayer.ui.video_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import us.kikin.apps.android.bgplayer.R
import us.kikin.apps.android.bgplayer.models.VideoModel

@AndroidEntryPoint
class VideoDetailFragment : Fragment() {

    private val viewModel: VideoDetailViewModel by viewModels()
    private val args: VideoDetailFragmentArgs by navArgs()
    private lateinit var titleView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var videoImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getVideoById(args.videoId)
        viewModel.videoLiveData.observe(
            requireActivity(),
            {
                bindVideo(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video_detail, container, false)
        titleView = view.findViewById(R.id.video_title)
        descriptionView = view.findViewById(R.id.video_description)
        videoImageView = view.findViewById(R.id.video_thumbnail)

        return view
    }

    private fun bindVideo(video: VideoModel) {
        titleView.text = video.name
        descriptionView.text = video.description
        videoImageView.load(video.thumbnailUrl) {
            crossfade(true)
        }
    }
}
