package com.trendyol.vsh.interview.project.core.data

import com.trendyol.vsh.interview.project.core.data.api.TrendyolService
import com.trendyol.vsh.interview.project.core.data.model.HomeLayout
import com.trendyol.vsh.interview.project.core.data.model.Product
import com.trendyol.vsh.interview.project.core.data.model.factory.ModelConverter
import com.trendyol.vsh.interview.project.core.data.room.TrendyolDatabase
import com.trendyol.vsh.interview.project.core.di.module.HomeScreenConverter
import com.trendyol.vsh.interview.project.core.di.module.KtorTrendyolApiService
import com.trendyol.vsh.interview.project.core.di.module.ProductItemsConverter
import com.trendyol.vsh.interview.project.core.logger.Logger
import com.trendyol.vsh.interview.project.core.ui.model.HomeScreenLayout
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject

private const val TAG = "TrendyolRepository"


internal class TrendyolRepositoryImpl @Inject constructor(
    @KtorTrendyolApiService private val trendyolService: TrendyolService,
    @HomeScreenConverter private val homeScreenLayoutConverter: ModelConverter<@JvmWildcard HomeLayout, @JvmWildcard HomeScreenLayout>,
    @ProductItemsConverter private val productItemsConverter: ModelConverter<List<@JvmWildcard Product>, List<@JvmWildcard ProductItem>>,
    private val database: TrendyolDatabase,
    private val logger: Logger
) : TrendyolRepository {

    override suspend fun getHomeLayout(): Result<HomeScreenLayout> =
        apiCall(homeScreenLayoutConverter) {
            trendyolService.getHomeLayout()
        }


    private suspend fun <BO, DTO> apiCall(
        converter: ModelConverter<DTO, BO>,
        method: suspend () -> DTO
    ): Result<BO> = try {
        coroutineScope {

            val result = async(Dispatchers.IO) {
                val delayMillis = (3000L..4000L).random()
                delay(delayMillis)
                method.invoke()
            }
            val data = result.await()

            return@coroutineScope Result.success(converter.convert(data))
        }
    } catch (e: Exception) {
        logger.e(TAG, "unable to download layout", e)
        Result.failure(IllegalArgumentException("Fail to download layout"))
    }

    override suspend fun getProducts(productsUrl: String): Result<List<ProductItem>> {
        logger.d(TAG, "getProducts, with url: $productsUrl")

        val productsResult = apiCall(productItemsConverter) {
            trendyolService.getProductListing(productsUrl)
        }

        if (productsResult.isSuccess) {
            database.productsDao().insertAll(productsResult.getOrDefault(emptyList()))
            logger.d(TAG, "${productsResult.getOrNull()?.size} products were inserted")
        }

        return productsResult
    }

    override suspend fun getProduct(productId: Int): ProductItem =
        coroutineScope {
            val delayMillis = (1000L..2000L).random()
            delay(delayMillis)
            database.productsDao().product(productId)
        }
}
