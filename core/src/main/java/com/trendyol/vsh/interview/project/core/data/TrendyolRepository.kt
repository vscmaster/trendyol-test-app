package com.trendyol.vsh.interview.project.core.data

import com.trendyol.vsh.interview.project.core.ui.model.HomeScreenLayout
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem

interface TrendyolRepository {

    suspend fun getHomeLayout(): Result<HomeScreenLayout>
    suspend fun getProducts(productsUrl: String): Result<List<ProductItem>>
    suspend fun getProduct(productId: Int): ProductItem?
}
