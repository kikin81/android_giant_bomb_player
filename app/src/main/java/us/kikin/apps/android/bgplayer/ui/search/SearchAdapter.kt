package us.kikin.apps.android.bgplayer.ui.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.ui.videos.VideoComparator
import us.kikin.apps.android.bgplayer.ui.videos.VideoItemClickListener

class SearchAdapter(
    private val listener: VideoItemClickListener
) : PagingDataAdapter<VideoModel, SearchViewHolder>(VideoComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder.from(parent)

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position) as VideoModel
        holder.bind(item, listener)
    }
}
