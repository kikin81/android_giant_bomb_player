package us.kikin.apps.android.bgplayer.ui.show

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import us.kikin.apps.android.bgplayer.models.ShowModel
import us.kikin.apps.android.bgplayer.models.VideoModel
import us.kikin.apps.android.bgplayer.ui.videos.VideoItemClickListener
import us.kikin.apps.android.bgplayer.ui.videos.VideoViewHolder
import us.kikin.apps.android.bgplayer.util.exhaustive

class ShowAdapter(
    private val videoClickListener: VideoItemClickListener,
    private val showClickListener: ShowItemClickListener
) : ListAdapter<ShowItem, RecyclerView.ViewHolder>(ShowDiffCallback()) {

    companion object {
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }

    private val adapterScope = CoroutineScope(Dispatchers.Default)

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
                val item = getItem(position) as ShowItem.Header
                holder.bind(item.show, showClickListener)
            }
            is VideoViewHolder -> {
                val item = getItem(position) as ShowItem.VideoItem
                holder.bind(item.video, videoClickListener, false)
            }
            else -> throw ClassCastException("Unknown holder type $holder")
        }.exhaustive
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ShowItem.Header -> ITEM_VIEW_TYPE_HEADER
            is ShowItem.VideoItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun addHeaderAndSubmitList(show: ShowModel, newItems: List<VideoModel>) {
        adapterScope.launch {
            val items = when (newItems) {
                null -> listOf(ShowItem.Header(show))
                else -> listOf(ShowItem.Header(show)) + newItems.map { ShowItem.VideoItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
}
