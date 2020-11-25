package us.kikin.apps.android.bgplayer.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import us.kikin.apps.android.bgplayer.databinding.FragmentVideoListBinding
import us.kikin.apps.android.bgplayer.models.ShowModel

@AndroidEntryPoint
class VideoListFragment : Fragment(), VideoItemClickListener {

    private var _binding: FragmentVideoListBinding? = null
    private var videoJob: Job? = null
    private val binding get() = requireNotNull(_binding) {
        throw IllegalStateException("Cannot access binding")
    }
    private val viewModel: VideoViewModel by viewModels()
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

        getLatestVideos()
        return view
    }

    private fun getLatestVideos() {
        videoJob?.cancel()
        videoJob = lifecycleScope.launch {
            viewModel.getLatestVideos().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onVideoClicked(videoId: Long) {
        val action = VideoListFragmentDirections.videoDetailAction(videoId)
        findNavController().navigate(action)
    }

    override fun onVideoShowClicked(showModel: ShowModel) {
        val action = VideoListFragmentDirections.videoShowAction(showModel.id, showModel.name)
        findNavController().navigate(action)
    }
}
