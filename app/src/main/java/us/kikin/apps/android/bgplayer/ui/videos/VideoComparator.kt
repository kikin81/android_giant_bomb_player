package us.kikin.apps.android.bgplayer.ui.videos

import androidx.recyclerview.widget.DiffUtil
import us.kikin.apps.android.bgplayer.models.VideoModel

class VideoComparator : DiffUtil.ItemCallback<VideoModel>() {

    override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean =
        oldItem == newItem
}
