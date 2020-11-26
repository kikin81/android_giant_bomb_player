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
            video = item
            handlers = listener
            videoThumbnail.load(item.thumbnailUrl) {
                crossfade(true)
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
