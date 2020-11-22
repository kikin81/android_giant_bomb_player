package us.kikin.apps.android.bgplayer.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import us.kikin.apps.android.bgplayer.R

@AndroidEntryPoint
class VideoListFragment : Fragment() {

    private val videoViewModel: VideoViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        videoViewModel.videoLiveData.observe(
            requireActivity(),
            {
                adapter.updateItems(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video_list, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = VideoAdapter()
        recyclerView.adapter = adapter

        return view
    }
}
