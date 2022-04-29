package com.trendyol.vsh.interview.project.core.data.model

import com.google.gson.annotations.SerializedName

internal data class DisplayOptions(
    @SerializedName("showProductPrice") val showProductPrice: Boolean? = null,
    @SerializedName("showProductFavoredButton") val showProductFavoredButton: Boolean? = null,
    @SerializedName("showClearButton") val showClearButton: Boolean? = null,
    @SerializedName("showCountdown") val showCountdown: Boolean? = null,
    @SerializedName("showCountOnTitle") val showCountOnTitle: Boolean? = null

)