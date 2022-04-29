package com.trendyol.vsh.interview.project.core.data.model.factory

import com.trendyol.vsh.interview.project.core.data.model.BannerContents
import com.trendyol.vsh.interview.project.core.data.model.DisplayTypes.SINGLE
import com.trendyol.vsh.interview.project.core.data.model.WidgetTypes.BANNER
import com.trendyol.vsh.interview.project.core.data.model.Widgets
import com.trendyol.vsh.interview.project.core.ui.model.SingleBanner
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert
import org.junit.Test

class SingleBannerModelFactoryTest {
    private val widgetId = 7
    private val content1: BannerContents = mockk(relaxed = true)
    private val content2: BannerContents = mockk(relaxed = true)
    private val widgets = Widgets(
        id = widgetId,
        type = BANNER,
        displayType = SINGLE,
        bannerContents = listOf(content1, content2)
    )
    private val widgetsMock = spyk(widgets)

    @Test
    fun create() {

        val factory = SingleBannerModelFactory()
        val widget = factory.create(widgetsMock)
        Assert.assertTrue(widget is SingleBanner)

        val banner = widget as SingleBanner

        Assert.assertEquals(widgetId, banner.id)
        Assert.assertEquals(content1.title, banner.title)
        Assert.assertEquals(content1.imageUrl, banner.imageUrl)
        Assert.assertEquals(content1.displayOrder, banner.displayOrder)
        Assert.assertEquals(content1.bannerEventKey, banner.bannerEventKey)
    }
}