package us.kikin.apps.android.bgplayer.ui.videos

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import us.kikin.apps.android.bgplayer.models.VideoModel

class VideoAdapter(
    private val listener: VideoItemClickListener
) : RecyclerView.Adapter<VideoViewHolder>() {

    private val videos = ArrayList<VideoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VideoViewHolder.from(parent)

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = videos[position]
        holder.bind(item, listener)
        holder.itemView.setOnClickListener {
            listener.onVideoClicked(item.id)
        }
    }

    override fun getItemCount(): Int = videos.size

    fun updateItems(newItems: List<VideoModel>) {
        videos.clear()
        videos.addAll(newItems)
        notifyDataSetChanged()
    }
}
