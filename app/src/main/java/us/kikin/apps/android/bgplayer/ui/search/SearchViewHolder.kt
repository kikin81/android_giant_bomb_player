package us.kikin.apps.android.bgplayer.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import us.kikin.apps.android.bgplayer.databinding.ItemSearchResultBinding
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.ui.videos.VideoItemClickListener

class SearchViewHolder(
    private val binding: ItemSearchResultBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: VideoModel,
        listener: VideoItemClickListener
    ) {
        with(binding) {
            video = item
            clickHandler = listener
            videoThumbnail.load(item.thumbnailUrl)
        }
    }

    companion object {
        fun from(parent: ViewGroup): SearchViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSearchResultBinding.inflate(inflater, parent, false)
            return SearchViewHolder(binding)
        }
    }
}
