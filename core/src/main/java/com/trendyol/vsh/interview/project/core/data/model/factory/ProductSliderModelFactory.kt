package com.trendyol.vsh.interview.project.core.data.model.factory

import com.trendyol.vsh.interview.project.core.data.model.Widgets
import com.trendyol.vsh.interview.project.core.ui.model.ProductListing
import com.trendyol.vsh.interview.project.core.ui.model.ProductSlider
import com.trendyol.vsh.interview.project.core.ui.model.Widget
import javax.inject.Inject

internal class ProductSliderModelFactory @Inject constructor() : ModelFactory {

    override fun create(widgets: Widgets): Widget? {
        return if (widgets.fullServiceUrl != null) ProductSlider(
            id = widgets.id,
            fullServiceUrl = widgets.fullServiceUrl,
        ) else null
    }
}