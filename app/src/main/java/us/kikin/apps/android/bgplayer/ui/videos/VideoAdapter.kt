package us.kikin.apps.android.bgplayer.ui.videos

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import us.kikin.apps.android.bgplayer.models.VideoModel

class VideoAdapter(
    private val listener: VideoItemClickListener
) : PagingDataAdapter<VideoModel, VideoViewHolder>(VideoComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VideoViewHolder.from(parent)

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = getItem(position) as VideoModel
        holder.bind(item, listener)
    }
}
