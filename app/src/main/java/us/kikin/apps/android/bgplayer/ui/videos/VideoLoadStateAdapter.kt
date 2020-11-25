package us.kikin.apps.android.bgplayer.ui.videos

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class VideoLoadStateAdapter : LoadStateAdapter<ListStateViewHolder>() {

    override fun onBindViewHolder(holder: ListStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ListStateViewHolder {
        return ListStateViewHolder.create(parent)
    }
}
