package us.kikin.apps.android.bgplayer.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import us.kikin.apps.android.bgplayer.R
import us.kikin.apps.android.bgplayer.models.VideoShowModel

@AndroidEntryPoint
class VideoListFragment : Fragment(), VideoItemClickListener {

    private val videoViewModel: VideoViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video_list, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = VideoAdapter(this)
        recyclerView.adapter = adapter

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

    override fun onVideoClicked(videoId: Long) {
        val action = VideoListFragmentDirections.videoDetailAction(videoId)
        findNavController().navigate(action)
    }

    override fun onVideoShowClicked(showModel: VideoShowModel) {
        val action = VideoListFragmentDirections.videoShowAction(showModel.id, showModel.name)
        findNavController().navigate(action)
    }
}
