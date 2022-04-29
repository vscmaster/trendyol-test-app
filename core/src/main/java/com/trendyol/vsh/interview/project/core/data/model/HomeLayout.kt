package com.trendyol.vsh.interview.project.core.data.model

import com.google.gson.annotations.SerializedName

internal data class HomeLayout(
    @SerializedName("widgets") val widgets: List<Widgets> = arrayListOf(),
    @SerializedName("pagination") val pagination: Pagination? = Pagination(),
)
