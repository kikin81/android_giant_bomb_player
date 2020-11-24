package us.kikin.apps.android.bgplayer.ui.videos

import us.kikin.apps.android.bgplayer.models.VideoShowModel

interface VideoItemClickListener {
    fun onVideoClicked(videoId: Long)
    fun onVideoShowClicked(showModel: VideoShowModel)
}
