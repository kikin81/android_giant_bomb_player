package us.kikin.apps.android.bgplayer.ui.show

import androidx.recyclerview.widget.DiffUtil

class ShowDiffCallback : DiffUtil.ItemCallback<ShowItem>() {
    override fun areItemsTheSame(oldItem: ShowItem, newItem: ShowItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShowItem, newItem: ShowItem): Boolean {
        return oldItem == newItem
    }
}
