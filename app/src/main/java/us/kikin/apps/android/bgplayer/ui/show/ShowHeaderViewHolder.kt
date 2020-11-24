package us.kikin.apps.android.bgplayer.ui.show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.button.MaterialButton
import us.kikin.apps.android.bgplayer.R
import us.kikin.apps.android.bgplayer.models.VideoShowModel

class ShowHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): ShowHeaderViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_show_header, parent, false)
            return ShowHeaderViewHolder(view)
        }
    }

    private val showThumbnail: ImageView = view.findViewById(R.id.show_thumbnail)
    private val showName: TextView = view.findViewById(R.id.show_name)
    private val followButton: MaterialButton = view.findViewById(R.id.show_follow_button)

    fun bind(item: VideoShowModel, showClickListener: ShowItemClickListener) {
        showName.text = item.name
        showThumbnail.load(item.imageUrl) {
            crossfade(true)
        }
        followButton.setOnClickListener {
            showClickListener.onFollowShowClicked(item.id)
        }
    }
}
