package us.kikin.apps.android.bgplayer.ui.videos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.button.MaterialButton
import us.kikin.apps.android.bgplayer.R
import us.kikin.apps.android.bgplayer.models.VideoModel

class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun from(parent: ViewGroup): VideoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_video, parent, false)
            return VideoViewHolder(view)
        }
    }

    private val titleView: TextView = view.findViewById(R.id.video_title)
    private val lengthView: TextView = view.findViewById(R.id.video_length)
    private val thumbnailView: ImageView = view.findViewById(R.id.video_thumbnail)
    private val publishedView: TextView = view.findViewById(R.id.video_published_date)
    private val showButton: MaterialButton = view.findViewById(R.id.video_show)

    fun bind(
        item: VideoModel,
        listener: VideoItemClickListener,
        shouldShowShowButton: Boolean = true
    ) {
        titleView.text = item.name
        lengthView.text = item.runtimeDisplay
        publishedView.text = item.publishedRelativeDay
        if (item.showModel != null && shouldShowShowButton) {
            showButton.visibility = View.VISIBLE
            showButton.text = item.showModel.name
            showButton.setOnClickListener {
                listener.onVideoShowClicked(item.showModel)
            }
        } else {
            showButton.visibility = View.GONE
        }
        thumbnailView.load(item.thumbnailUrl) {
            crossfade(true)
        }
    }
}
