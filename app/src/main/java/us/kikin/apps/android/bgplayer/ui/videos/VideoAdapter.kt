package us.kikin.apps.android.bgplayer.ui.videos

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import us.kikin.apps.android.bgplayer.models.VideoModel

class VideoAdapter(
    private val listener: VideoItemClickListener
) : PagingDataAdapter<VideoModel, VideoViewHolder>(VIDEO_COMPARATOR) {

    private val videos = ArrayList<VideoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VideoViewHolder.from(parent)

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = videos[position]
        holder.bind(item, listener)
    }

    override fun getItemCount(): Int = videos.size

    fun updateItems(newItems: List<VideoModel>) {
        videos.clear()
        videos.addAll(newItems)
        notifyDataSetChanged()
    }

    companion object {
        private val VIDEO_COMPARATOR = object : DiffUtil.ItemCallback<VideoModel>() {
            override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean =
                oldItem == newItem
        }
    }
}
