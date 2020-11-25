package us.kikin.apps.android.bgplayer.ui.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import us.kikin.apps.android.bgplayer.databinding.ItemListStateFooterBinding

class ListStateViewHolder(
    private val binding: ItemListStateFooterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        with(binding) {
            if (loadState is LoadState.Error) {
                listErrorMessage.text = loadState.error.localizedMessage
            }
            listProgress.isVisible = loadState is LoadState.Loading
            listErrorMessage.isVisible = loadState is LoadState.Loading
        }
    }

    companion object {
        fun create(parent: ViewGroup): ListStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemListStateFooterBinding.inflate(inflater, parent, false)
            return ListStateViewHolder(binding)
        }
    }
}
