package us.kikin.apps.android.bgplayer.service

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleService

class PlayerService : LifecycleService() {
    companion object {

        private const val ARG_TITLE = "VIDEO_TITLE_KEY"
        private const val ARG_URI = "VIDEO_URI_KEY"
        private const val ARG_START_POSITION = "VIDEO_START_POSITION"

        @MainThread
        fun newIntent(
            context: Context,
            title: String,
            uriString: String,
            startPosition: Long
        ) = Intent(context, PlayerService::class.java).apply {
            putExtra(ARG_TITLE, title)
            putExtra(ARG_URI, Uri.parse(uriString))
            putExtra(ARG_START_POSITION, startPosition)
        }
    }
}
