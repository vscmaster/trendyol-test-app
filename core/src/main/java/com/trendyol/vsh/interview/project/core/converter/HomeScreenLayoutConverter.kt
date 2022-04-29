package com.trendyol.vsh.interview.project.core.converter

import com.trendyol.vsh.interview.project.core.data.model.HomeLayout
import com.trendyol.vsh.interview.project.core.data.model.factory.ModelConverter
import com.trendyol.vsh.interview.project.core.data.model.factory.ModelFactory
import com.trendyol.vsh.interview.project.core.di.module.ModelsFactory
import com.trendyol.vsh.interview.project.core.ui.model.HomeScreenLayout
import com.trendyol.vsh.interview.project.core.ui.model.Widget
import javax.inject.Inject

internal class HomeScreenLayoutConverter @Inject constructor(@ModelsFactory private val modelsFactory: ModelFactory) :
    ModelConverter<HomeLayout, HomeScreenLayout> {

    override fun convert(model: HomeLayout): HomeScreenLayout {
        val widgetsList = mutableListOf<Widget>()

        model.widgets.forEach { widgetsContent ->
            modelsFactory.create(widgetsContent)?.let { widgetsList.add(it) }
        }

        return HomeScreenLayout(widgets = widgetsList)
    }
}