package us.kikin.apps.android.bgplayer.ui.show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import us.kikin.apps.android.bgplayer.databinding.ItemShowHeaderBinding
import us.kikin.apps.android.bgplayer.models.VideoShowModel

class ShowHeaderViewHolder(
    private val binding: ItemShowHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): ShowHeaderViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemShowHeaderBinding.inflate(inflater, parent, false)
            return ShowHeaderViewHolder(binding)
        }
    }

    fun bind(item: VideoShowModel, showClickListener: ShowItemClickListener) {
        with(binding) {
            showName.text = item.name
            showThumbnail.load(item.imageUrl) {
                crossfade(true)
            }
            showFollowButton.setOnClickListener {
                showClickListener.onFollowShowClicked(item.id)
            }
        }
    }
}
