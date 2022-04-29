package com.trendyol.vsh.interview.project.core.data.model

import com.google.gson.annotations.SerializedName

internal data class Navigation(
    @SerializedName("deeplink") val deeplink: String? = null,
    @SerializedName("id") val id: Int = -1,
    @SerializedName("landingTitle") val landingTitle: String? = null,
    @SerializedName("navigationType") val navigationType: String? = null,
    @SerializedName("title") val title: String = ""
)