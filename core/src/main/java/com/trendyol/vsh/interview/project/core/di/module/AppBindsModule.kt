package com.trendyol.vsh.interview.project.core.di.module

import com.trendyol.vsh.interview.project.core.converter.HomeScreenLayoutConverter
import com.trendyol.vsh.interview.project.core.data.TrendyolRepository
import com.trendyol.vsh.interview.project.core.data.TrendyolRepositoryImpl
import com.trendyol.vsh.interview.project.core.data.model.DisplayTypes
import com.trendyol.vsh.interview.project.core.data.model.DisplayTypes.CAROUSEL
import com.trendyol.vsh.interview.project.core.data.model.DisplayTypes.LISTING
import com.trendyol.vsh.interview.project.core.data.model.DisplayTypes.SLIDER
import com.trendyol.vsh.interview.project.core.data.model.HomeLayout
import com.trendyol.vsh.interview.project.core.data.model.Product
import com.trendyol.vsh.interview.project.core.data.model.WidgetTypes.BANNER
import com.trendyol.vsh.interview.project.core.data.model.WidgetTypes.PRODUCT
import com.trendyol.vsh.interview.project.core.data.model.factory.*
import com.trendyol.vsh.interview.project.core.logger.Logger
import com.trendyol.vsh.interview.project.core.logger.TrendyolLogger
import com.trendyol.vsh.interview.project.core.ui.model.HomeScreenLayout
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Qualifier
import javax.inject.Singleton

@MapKey(unwrapValue = false)
internal annotation class ModelFactoryKey(val type: String, val displayType: String)

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ModelsFactory

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductItemConverter

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductItemsConverter

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HomeScreenConverter

@Module
@InstallIn(SingletonComponent::class)
internal interface AppBindsModule {

    @Binds
    @Singleton
    @IntoMap
    @ModelFactoryKey(type = BANNER, displayType = DisplayTypes.SINGLE)
    fun bindSingleBannerModelFactory(singleBannerModelFactory: SingleBannerModelFactory): ModelFactory

    @Binds
    @Singleton
    @IntoMap
    @ModelFactoryKey(PRODUCT, LISTING)
    fun bindProductListingModelFactory(productListingModelFactory: ProductListingModelFactory): ModelFactory

    @Binds
    @Singleton
    @IntoMap
    @ModelFactoryKey(PRODUCT, SLIDER)
    fun bindProductSliderModelFactory(productSliderModelFactory: ProductSliderModelFactory): ModelFactory

    @Binds
    @Singleton
    @IntoMap
    @ModelFactoryKey(BANNER, CAROUSEL)
    fun bindCarouselBannerModelFactory(carouselBannerModelFactory: CarouselBannerModelFactory): ModelFactory

    @Binds
    @Singleton
    @ModelsFactory
    fun bindModelsFactory(modelsFactory: ModelsFactoryImpl): ModelFactory

    @Binds
    @Singleton
    @ProductItemConverter
    fun bindProductItemModelConverter(productItemModelConverter: ProductItemModelConverter): ModelConverter<@JvmWildcard Product, @JvmWildcard ProductItem>

    @Binds
    @Singleton
    @ProductItemsConverter
    fun bindProductItemsModelConverter(productItemsModelConverter: ProductItemsModelConverter): ModelConverter<@JvmWildcard List<@JvmWildcard Product>, @JvmWildcard List<@JvmWildcard ProductItem>>

    @Binds
    @Singleton
    @HomeScreenConverter
    fun bindHomeScreenLayoutConverter(homeScreenLayoutConverter: HomeScreenLayoutConverter): ModelConverter<@JvmWildcard HomeLayout, @JvmWildcard HomeScreenLayout>

    @Binds
    @Singleton
    fun bindTrendyolRepository(trendyolRepository: TrendyolRepositoryImpl): TrendyolRepository

    @Binds
    @Singleton
    fun bindLogger(trendyolLogger: TrendyolLogger): Logger
}