package us.kikin.apps.android.bgplayer.ui.videos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import us.kikin.apps.android.bgplayer.R
import us.kikin.apps.android.bgplayer.models.VideoModel

class VideoAdapter(
    private val listener: VideoListClickListener
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    interface VideoListClickListener {
        fun onVideoClicked(videoId: Long)
    }

    private val videos = ArrayList<VideoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = videos[position]
        holder.bind(item)
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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleView: TextView = view.findViewById(R.id.video_title)
        private val lengthView: TextView = view.findViewById(R.id.video_length)
        private val thumbnailView: ImageView = view.findViewById(R.id.video_thumbnail)

        fun bind(item: VideoModel) {
            titleView.text = item.name
            lengthView.text = item.length.toString()
            thumbnailView.load(item.thumbnailUrl) {
                crossfade(true)
            }
        }
    }
}
