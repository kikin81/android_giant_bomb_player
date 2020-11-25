package us.kikin.apps.android.bgplayer.ui.show

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import us.kikin.apps.android.bgplayer.ui.videos.VideoItemClickListener
import us.kikin.apps.android.bgplayer.ui.videos.VideoViewHolder
import us.kikin.apps.android.bgplayer.util.exhaustive

class ShowAdapter(
    private val videoClickListener: VideoItemClickListener,
    private val showClickListener: ShowItemClickListener
) : PagingDataAdapter<ShowUiModel, RecyclerView.ViewHolder>(ShowDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> ShowHeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> VideoViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ShowHeaderViewHolder -> {
                val item = getItem(position) as ShowUiModel.Header
                holder.bind(item.show, showClickListener)
            }
            is VideoViewHolder -> {
                val item = getItem(position) as ShowUiModel.VideoUiModel
                holder.bind(item.video, videoClickListener, false)
            }
            else -> throw ClassCastException("Unknown holder type $holder")
        }.exhaustive
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ShowUiModel.Header -> ITEM_VIEW_TYPE_HEADER
            is ShowUiModel.VideoUiModel -> ITEM_VIEW_TYPE_ITEM
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    companion object {
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }
}
