package us.kikin.apps.android.bgplayer.ui.videos

import us.kikin.apps.android.bgplayer.models.ShowModel

interface VideoItemClickListener {
    fun onVideoClicked(videoId: Long)
    fun onVideoShowClicked(showModel: ShowModel)
}
