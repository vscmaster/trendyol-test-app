package com.trendyol.vsh.interview.project.core.data.model.factory

import com.trendyol.vsh.interview.project.core.data.model.Product
import com.trendyol.vsh.interview.project.core.di.module.ProductItemConverter
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem
import javax.inject.Inject

internal class ProductItemsModelConverter @Inject constructor(@ProductItemConverter private val productConverter: ModelConverter<@JvmWildcard Product, @JvmWildcard ProductItem>) :
    ModelConverter<List<@JvmWildcard Product>, List<@JvmWildcard ProductItem>> {

    override fun convert(model: List<Product>): List<ProductItem> =
        model.map { product -> productConverter.convert(product) }
}