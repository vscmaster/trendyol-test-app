package com.trendyol.vsh.interview.project.core.data.model

import com.google.gson.annotations.SerializedName

internal data class Widgets(
    @SerializedName("bannerContents") val bannerContents: List<BannerContents> = arrayListOf(),
    @SerializedName("displayType") val displayType: String,
    @SerializedName("eventKey") val eventKey: String? = null,
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("displayCount") val displayCount: Int? = null,
    @SerializedName("displayOptions") val displayOptions: DisplayOptions? = DisplayOptions(),
    @SerializedName("startDate") val startDate: String? = null,
    @SerializedName("endDate") val endDate: String? = null,
    @SerializedName("width") val width: Int? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("refreshRequired") val refreshRequired: Boolean? = null,
    @SerializedName("fullServiceUrl") val fullServiceUrl: String? = null
)