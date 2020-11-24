package us.kikin.apps.android.bgplayer.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import us.kikin.apps.android.bgplayer.databinding.FragmentVideoListBinding
import us.kikin.apps.android.bgplayer.models.VideoShowModel

@AndroidEntryPoint
class VideoListFragment : Fragment(), VideoItemClickListener {

    private var _binding: FragmentVideoListBinding? = null
    private val binding get() = _binding!!
    private val videoViewModel: VideoViewModel by viewModels()
    private lateinit var adapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoListBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = VideoAdapter(this)
        binding.recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoViewModel.videoListLiveData.observe(
            viewLifecycleOwner,
            {
                adapter.updateItems(it)
            }
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroy()
    }

    override fun onVideoClicked(videoId: Long) {
        val action = VideoListFragmentDirections.videoDetailAction(videoId)
        findNavController().navigate(action)
    }

    override fun onVideoShowClicked(showModel: VideoShowModel) {
        val action = VideoListFragmentDirections.videoShowAction(showModel.id, showModel.name)
        findNavController().navigate(action)
    }
}
