package us.kikin.apps.android.bgplayer.ui.show

import androidx.recyclerview.widget.DiffUtil

class ShowDiffCallback : DiffUtil.ItemCallback<ShowUiModel>() {
    override fun areItemsTheSame(oldUiModel: ShowUiModel, newUiModel: ShowUiModel): Boolean {
        return oldUiModel.id == newUiModel.id
    }

    override fun areContentsTheSame(oldUiModel: ShowUiModel, newUiModel: ShowUiModel): Boolean {
        return oldUiModel == newUiModel
    }
}
