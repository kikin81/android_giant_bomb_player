package us.kikin.apps.android.bgplayer.models

import us.kikin.apps.android.bgplayer.network.VideoShowDto

data class VideoShowModel(
    val id: Long,
    val name: String,
    val imageUrl: String
) {
    constructor(dto: VideoShowDto) :
        this(
            dto.id,
            dto.name,
            dto.imageDto.screenUrl
        )
}
