package us.kikin.apps.android.bgplayer.ui.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import us.kikin.apps.android.bgplayer.databinding.FragmentShowBinding
import us.kikin.apps.android.bgplayer.models.ShowModel
import us.kikin.apps.android.bgplayer.ui.videos.VideoItemClickListener

@AndroidEntryPoint
class ShowListFragment : Fragment(), VideoItemClickListener, ShowItemClickListener {

    private var _binding: FragmentShowBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var showJob: Job? = null
    private val viewModel: ShowViewModel by viewModels()
    private val adapter = ShowAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowBinding.inflate(inflater, container, false)
        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showLiveData.observe(viewLifecycleOwner, { getVideosForShow(it) })
    }

    private fun initAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun getVideosForShow(showModel: ShowModel) {
        showJob?.cancel()
        showJob = lifecycleScope.launch {
            viewModel.getVideosForShow(showModel).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onVideoClicked(videoId: Long) {
        val action = ShowListFragmentDirections.videoDetailAction(videoId)
        findNavController().navigate(action)
    }

    override fun onVideoShowClicked(showModel: ShowModel) {
        throw UnsupportedOperationException("Show Clicked not supported")
    }

    override fun onFollowShowClicked(showId: Long) {
        Toast.makeText(activity, "Followed clicked", Toast.LENGTH_SHORT).show()
    }
}
