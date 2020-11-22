package us.kikin.apps.android.bgplayer.ui.videos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import us.kikin.apps.android.bgplayer.R
import us.kikin.apps.android.bgplayer.models.VideoModel

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private val videos = ArrayList<VideoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = videos[position]
        holder.titleView.text = item.name
        holder.lengthView.text = item.length.toString()
        holder.thumbnailView.load(item.thumbnailUrl) {
            crossfade(true)
            transformations(RoundedCornersTransformation(8f, 8f, 8f, 8f))
        }
    }

    override fun getItemCount(): Int = videos.size

    fun updateItems(newItems: List<VideoModel>) {
        videos.clear()
        videos.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.video_title)
        val lengthView: TextView = view.findViewById(R.id.video_length)
        val thumbnailView: ImageView = view.findViewById(R.id.video_thumbnail)

        override fun toString(): String {
            return super.toString() + " '" + lengthView.text + "'"
        }
    }
}
