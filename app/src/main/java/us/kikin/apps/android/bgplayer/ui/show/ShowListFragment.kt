package us.kikin.apps.android.bgplayer.ui.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import us.kikin.apps.android.bgplayer.R
import us.kikin.apps.android.bgplayer.models.VideoShowModel
import us.kikin.apps.android.bgplayer.ui.videos.VideoItemClickListener

@AndroidEntryPoint
class ShowListFragment : Fragment(), VideoItemClickListener, ShowItemClickListener {

    private val viewModel: ShowViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_show, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        adapter = ShowAdapter(this, this)
        recyclerView.adapter = adapter

        return view
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
