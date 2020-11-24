package us.kikin.apps.android.bgplayer.ui.show

import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.models.VideoShowModel

sealed class ShowItem {
    abstract val id: Long

    data class VideoItem(val video: VideoModel) : ShowItem() {
        override val id = video.id
    }

    data class Header(val show: VideoShowModel) : ShowItem() {
        override val id = show.id
    }
}

data class ShowDataHolder(
    val videos: List<VideoModel>,
    val show: VideoShowModel
)
