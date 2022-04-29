package com.trendyol.vsh.interview.project.core.data.model.factory

import com.trendyol.vsh.interview.project.core.data.model.Product
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

internal class ProductItemModelConverter @Inject constructor() :
    ModelConverter<@JvmSuppressWildcards Product, @JvmSuppressWildcards ProductItem> {

    override fun convert(model: Product): ProductItem = with(model) {
        return ProductItem(
            boutiqueId = boutiqueId,
            brandName = brandName,
            brandId = brandId,
            categoryName = categoryName,
            contentId = contentId,
            id = id,
            imageUrl = imageUrl,
            imageUrls = imageUrls,
            marketPrice = marketPrice?.let { normalizeCurrency(it) },
            name = name,
            salePrice = salePrice?.let { normalizeCurrency(it) },
            originalPrice = mOriginalPrice?.let { normalizeCurrency(it) },
            stockStatus = stockStatus,
            averageRating = averageRating,
            ratingCount = ratingCount,
            discountedPrice = discountedPrice?.let { normalizeCurrency(it) },
            newDiscountedPrice = newDiscountedPrice?.let { normalizeCurrency(it) },
        )
    }

    private fun normalizeCurrency(value: Double): String =
        BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toString() + " ₴"
}