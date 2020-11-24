package us.kikin.apps.android.bgplayer.ui.videos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import us.kikin.apps.android.bgplayer.databinding.ItemVideoBinding
import us.kikin.apps.android.bgplayer.models.VideoModel

class VideoViewHolder(
    private val binding: ItemVideoBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): VideoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemVideoBinding.inflate(inflater)
            return VideoViewHolder(binding)
        }
    }

    fun bind(
        item: VideoModel,
        listener: VideoItemClickListener,
        shouldShowShowButton: Boolean = true
    ) {
        with(binding) {
            binding.videoTitle.text = item.name
            binding.videoLength.text = item.runtimeDisplay
            binding.videoPublishedDate.text = item.publishedRelativeDay
            videoThumbnail.load(item.thumbnailUrl) {
                crossfade(true)
            }
            if (item.showModel != null && shouldShowShowButton) {
                videoShow.visibility = View.VISIBLE
                videoShow.text = item.showModel.name
                videoShow.setOnClickListener {
                    listener.onVideoShowClicked(item.showModel)
                }
            } else {
                videoShow.visibility = View.GONE
            }
        }
    }
}
