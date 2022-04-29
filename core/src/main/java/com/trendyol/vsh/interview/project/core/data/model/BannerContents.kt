package com.trendyol.vsh.interview.project.core.data.model

import com.google.gson.annotations.SerializedName

internal data class BannerContents(
    @SerializedName("bannerEventKey") val bannerEventKey: String? = null,
    @SerializedName("displayOrder") val displayOrder: Int? = null,
    @SerializedName("imageUrl") val imageUrl: String? = null,
    @SerializedName("title") val title: String,
    @SerializedName("navigation") val navigation: Navigation = Navigation()
)