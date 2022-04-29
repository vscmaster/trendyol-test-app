package com.trendyol.vsh.interview.project.core.ui.model

class SingleBanner(
    id: Int,
    val bannerEventKey: String,
    val displayOrder: Int,
    val imageUrl: String? = null,
    val title: String,
) : Widget(id)