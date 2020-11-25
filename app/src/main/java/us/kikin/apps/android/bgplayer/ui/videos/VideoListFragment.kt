package us.kikin.apps.android.bgplayer.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import us.kikin.apps.android.bgplayer.databinding.FragmentVideoListBinding
import us.kikin.apps.android.bgplayer.models.ShowModel

@AndroidEntryPoint
class VideoListFragment : Fragment(), VideoItemClickListener {

    private var _binding: FragmentVideoListBinding? = null
    private var videoJob: Job? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: VideoViewModel by viewModels()
    private val adapter = VideoAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoListBinding.inflate(inflater, container, false)
        initAdapter()
        getLatestVideos()

        return binding.root
    }

    private fun initAdapter() {
        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = VideoLoadStateAdapter(),
            footer = VideoLoadStateAdapter()
        )
        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.listProgress.isVisible = loadState.source.refresh is LoadState.Loading

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
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
