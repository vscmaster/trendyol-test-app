package com.trendyol.vsh.interview.project.ui.section.product.listing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trendyol.vsh.interview.project.core.data.TrendyolRepository
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem
import com.trendyol.vsh.interview.project.ui.section.ListingViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

private const val TAG = "ProductListingViewModel"

class ProductListingViewModel @AssistedInject constructor(
    private val trendyolRepository: TrendyolRepository,
    @Assisted val productsUrl: String,
) : ListingViewModel<ProductItem>() {

    @AssistedFactory
    interface ProductListingViewModelAssistedFactory {

        fun create(productsUrl: String): ProductListingViewModel
    }

    init {
        Log.d(TAG, "refreshLayout")
        refreshLayout(mapOf("productsUrl" to productsUrl))
    }

    override suspend fun repositoryMethod(args: Map<String, Any>): Result<List<ProductItem>> {
        val productsUrl: String = args["productsUrl"] as String
        return trendyolRepository.getProducts(productsUrl = productsUrl)
    }

    companion object {
        fun provideFactory(
            assistedFactory: ProductListingViewModelAssistedFactory,
            productsUrl: String,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(productsUrl) as T
            }
        }
    }
}