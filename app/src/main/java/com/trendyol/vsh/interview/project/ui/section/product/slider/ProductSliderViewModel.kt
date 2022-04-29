package com.trendyol.vsh.interview.project.ui.section.product.slider

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trendyol.vsh.interview.project.core.data.TrendyolRepository
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem
import com.trendyol.vsh.interview.project.ui.section.ListingViewModel
import com.trendyol.vsh.interview.project.ui.section.product.listing.ProductListingViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

private const val TAG = "ProductSliderViewModel"

class ProductSliderViewModel @AssistedInject constructor(
    private val trendyolRepository: TrendyolRepository,
    productsUrl: String,
) : ListingViewModel<ProductItem>() {

    init {
        Log.d(TAG, "refreshLayout")
        refreshLayout(mapOf("productsUrl" to productsUrl))
    }

    @AssistedFactory
    interface ProductSliderViewModelAssistedFactory {

        fun create(productsUrl: String): ProductListingViewModel
    }

    override suspend fun repositoryMethod(args: Map<String, Any>): Result<List<ProductItem>> {
        val productsUrl: String = args["productsUrl"] as String
        return trendyolRepository.getProducts(productsUrl = productsUrl)
    }

    companion object {
        fun provideFactory(
            assistedFactory: ProductSliderViewModelAssistedFactory,
            productsUrl: String,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(productsUrl) as T
            }
        }
    }
}