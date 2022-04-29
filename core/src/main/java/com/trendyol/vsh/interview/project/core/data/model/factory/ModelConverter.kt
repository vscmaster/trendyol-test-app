package com.trendyol.vsh.interview.project.core.data.model.factory

internal interface ModelConverter<in From, out To> {
    fun convert(model: From): To
}