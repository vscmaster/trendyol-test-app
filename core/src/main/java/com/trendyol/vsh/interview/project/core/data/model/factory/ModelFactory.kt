package com.trendyol.vsh.interview.project.core.data.model.factory

import com.trendyol.vsh.interview.project.core.data.model.Widgets
import com.trendyol.vsh.interview.project.core.ui.model.Widget

internal interface ModelFactory {
    fun create(widgets: Widgets): Widget?
}