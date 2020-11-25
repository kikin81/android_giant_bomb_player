package us.kikin.apps.android.bgplayer.ui.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import us.kikin.apps.android.bgplayer.databinding.ItemVideoBinding
import us.kikin.apps.android.bgplayer.models.VideoModel

class VideoViewHolder(
    private val binding: ItemVideoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: VideoModel,
        listener: VideoItemClickListener,
        shouldShowShowButton: Boolean = true
    ) {
        with(binding) {
            videoTitle.text = item.name
            videoLength.text = item.runtimeDisplay
            videoPublishedDate.text = item.publishedInfoDisplay
            videoThumbnail.load(item.thumbnailUrl) {
                crossfade(true)
            }
            root.setOnClickListener {
                listener.onVideoClicked(item.id)
            }
            if (item.showModel != null && shouldShowShowButton) {
                videoShow.text = item.showModel.name
                videoShow.setOnClickListener {
                    listener.onVideoShowClicked(item.showModel)
                }
            }
            videoShow.isVisible = item.showModel != null && shouldShowShowButton
        }
    }

    companion object {
        fun from(parent: ViewGroup): VideoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemVideoBinding.inflate(inflater, parent, false)
            return VideoViewHolder(binding)
        }
    }
}
