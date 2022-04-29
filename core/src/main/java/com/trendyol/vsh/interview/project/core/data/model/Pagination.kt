package com.trendyol.vsh.interview.project.core.data.model

import com.google.gson.annotations.SerializedName

internal data class Pagination(
    @SerializedName("currentPage") val currentPage: Int? = null,
    @SerializedName("pageSize") val pageSize: Int? = null,
    @SerializedName("totalCount") val totalCount: Int? = null
)