package com.trendyol.vsh.interview.project.core.data.model.factory

import com.trendyol.vsh.interview.project.core.data.model.Widgets
import com.trendyol.vsh.interview.project.core.ui.model.SingleBanner
import com.trendyol.vsh.interview.project.core.ui.model.Widget
import javax.inject.Inject

internal class SingleBannerModelFactory @Inject constructor() : ModelFactory {

    override fun create(widgets: Widgets): Widget {
        val bannerContent = widgets.bannerContents.firstOrNull()
        return SingleBanner(
            id = widgets.id,
            bannerEventKey = bannerContent?.bannerEventKey ?: "",
            displayOrder = bannerContent?.displayOrder ?: 0,
            imageUrl = bannerContent?.imageUrl,
            title = bannerContent?.title ?: ""
        )
    }
}