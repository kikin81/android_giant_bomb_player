package us.kikin.apps.android.bgplayer.ui.show

import us.kikin.apps.android.bgplayer.models.ShowModel
import us.kikin.apps.android.bgplayer.models.VideoModel

sealed class ShowUiModel {
    abstract val id: Long

    data class VideoUiModel(val video: VideoModel) : ShowUiModel() {
        override val id = video.id
    }

    data class Header(val show: ShowModel) : ShowUiModel() {
        override val id = show.id
    }
}
