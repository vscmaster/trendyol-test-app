package com.trendyol.vsh.interview.project.core.data

import com.trendyol.vsh.interview.project.core.data.room.TrendyolDatabase
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem

val productItem = ProductItem(id = 100, imageUrl = "https://base/imageUrl", name = "Test product", averageRating = 4.5, ratingCount = 256, salePrice = "1024.99")
val productItem1 = ProductItem(id = 111, imageUrl = "https://base/imageUrl1", name = "Test product 1", averageRating = 4.4, ratingCount = 201, salePrice = "1001.99")

internal suspend fun insertProduct(db: TrendyolDatabase) = db.productsDao().insert(productItem)
