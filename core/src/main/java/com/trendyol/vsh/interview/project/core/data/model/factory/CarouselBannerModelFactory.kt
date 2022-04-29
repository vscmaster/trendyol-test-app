package com.trendyol.vsh.interview.project.core.data.model.factory

import com.trendyol.vsh.interview.project.core.data.model.Widgets
import com.trendyol.vsh.interview.project.core.ui.model.CarouselBanner
import com.trendyol.vsh.interview.project.core.ui.model.CarouselBannerContent
import com.trendyol.vsh.interview.project.core.ui.model.Widget
import javax.inject.Inject

internal class CarouselBannerModelFactory @Inject constructor() : ModelFactory {

    override fun create(widgets: Widgets): Widget {
        val bannerContents = widgets.bannerContents
        val carouselBannerContents = mutableListOf<CarouselBannerContent>()
        bannerContents.forEach { content ->
            carouselBannerContents.add(
                CarouselBannerContent(
                    id = content.navigation.id,
                    imageUrl = content.imageUrl ?: "",
                    title = content.navigation.title,
                )
            )
        }
        return CarouselBanner(
            carouselBannerContents = carouselBannerContents,
            displayCount = widgets.displayCount ?: 1,
            id = widgets.id,
        )
    }
}