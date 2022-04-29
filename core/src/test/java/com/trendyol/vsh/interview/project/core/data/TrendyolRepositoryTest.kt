package com.trendyol.vsh.interview.project.core.data

import com.trendyol.vsh.interview.project.core.data.api.TrendyolService
import com.trendyol.vsh.interview.project.core.data.model.HomeLayout
import com.trendyol.vsh.interview.project.core.data.model.Product
import com.trendyol.vsh.interview.project.core.data.model.factory.ModelConverter
import com.trendyol.vsh.interview.project.core.data.room.ProductEntryDao
import com.trendyol.vsh.interview.project.core.data.room.TrendyolDatabase
import com.trendyol.vsh.interview.project.core.logger.Logger
import com.trendyol.vsh.interview.project.core.ui.model.HomeScreenLayout
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem
import io.ktor.network.sockets.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class TrendyolRepositoryTest {

    private val productsUrl = "https://products"
    private val failProductsUrl = "https://products_fail"
    private val productId = 101

    private lateinit var repository: TrendyolRepository
    private val trendyolService: TrendyolService = mockk()
    private val homeScreenLayoutConverter: ModelConverter<HomeLayout, HomeScreenLayout> = mockk()
    private val productItemsConverter: ModelConverter<List<Product>, List<ProductItem>> = mockk()
    private val database: TrendyolDatabase = mockk()
    private val logger: Logger = mockk(relaxed = true)
    private val productEntryDao: ProductEntryDao = mockk(relaxed = true)

    private val homeLayout: HomeLayout = mockk()
    private val homeScreenLayout: HomeScreenLayout = mockk()
    private val products: List<Product> = listOf(mockk(), mockk())
    private val productItems: List<ProductItem> = listOf(mockk(), mockk())
    private val product: ProductItem = mockk()

    @Before
    fun setup() {
        repository = TrendyolRepositoryImpl(
            trendyolService = trendyolService,
            homeScreenLayoutConverter = homeScreenLayoutConverter,
            productItemsConverter = productItemsConverter,
            database = database,
            logger
        )

        coEvery { homeScreenLayoutConverter.convert(homeLayout) } returns homeScreenLayout
        coEvery { productItemsConverter.convert(products) } returns productItems
        coEvery { trendyolService.getHomeLayout(any(), any()) } returns homeLayout
        coEvery { trendyolService.getProductListing(productsUrl) } returns products
        coEvery { trendyolService.getProductListing(failProductsUrl) }.throws(
            ConnectTimeoutException("url")
        )
        coEvery { database.productsDao() } returns productEntryDao
        coEvery { productEntryDao.product(productId) } returns product
    }

    @Test
    fun getHomeLayoutTest() = runTest {
        val apiResult = repository.getHomeLayout()

        Assert.assertTrue(apiResult.isSuccess)
        Assert.assertEquals(homeScreenLayout, apiResult.getOrNull())
    }

    @Test
    fun getProductsTest() = runTest {
        val apiResult = repository.getProducts(productsUrl)
        Assert.assertTrue(apiResult.isSuccess)

        Assert.assertEquals(productItems, apiResult.getOrNull())

        coVerify { productEntryDao.insertAll(productItems) }
    }

    @Test
    fun failsProductsTest() = runTest {
        val apiResult = repository.getProducts(failProductsUrl)
        Assert.assertTrue(apiResult.isFailure)

        Assert.assertTrue(apiResult.exceptionOrNull() is IllegalArgumentException)

        coVerify(exactly = 0) { productEntryDao.insertAll(productItems) }
    }

    @Test
    fun getProductTest() = runTest {
        val entry = repository.getProduct(productId)
        Assert.assertNotNull(entry)
        Assert.assertEquals(product, entry)
    }
}