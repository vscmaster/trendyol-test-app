package com.trendyol.vsh.interview.project.core.data.model.factory

import com.trendyol.vsh.interview.project.core.data.model.Widgets
import com.trendyol.vsh.interview.project.core.di.module.ModelFactoryKey
import com.trendyol.vsh.interview.project.core.ui.model.Widget
import javax.inject.Inject

internal class ModelsFactoryImpl @Inject constructor(private val factories: Map<ModelFactoryKey, @JvmSuppressWildcards ModelFactory>) :
    ModelFactory {

    override fun create(widgets: Widgets): Widget? {
        return factories[ModelFactoryKey(
            widgets.type,
            widgets.displayType
        )]?.create(widgets)
    }
}