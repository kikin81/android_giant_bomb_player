package us.kikin.apps.android.bgplayer.ui.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import us.kikin.apps.android.bgplayer.databinding.FragmentShowBinding
import us.kikin.apps.android.bgplayer.models.VideoShowModel
import us.kikin.apps.android.bgplayer.ui.videos.VideoItemClickListener

@AndroidEntryPoint
class ShowListFragment : Fragment(), VideoItemClickListener, ShowItemClickListener {

    private var _binding: FragmentShowBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShowViewModel by viewModels()
    private lateinit var adapter: ShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowBinding.inflate(inflater, container, false)
        adapter = ShowAdapter(this, this)
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.videoListLiveData.observe(
            viewLifecycleOwner,
            {
                adapter.addHeaderAndSubmitList(
                    it.show,
                    it.videos
                )
            }
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onVideoClicked(videoId: Long) {
        val action = ShowListFragmentDirections.videoDetailAction(videoId)
        findNavController().navigate(action)
    }

    override fun onVideoShowClicked(showModel: VideoShowModel) {
        throw UnsupportedOperationException("Show Clicked not supported")
    }

    override fun onFollowShowClicked(showId: Long) {
        Toast.makeText(activity, "Followed clicked", Toast.LENGTH_SHORT).show()
    }
}
