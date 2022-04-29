package com.trendyol.vsh.interview.project.ui.section.product.slider

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.skydoves.landscapist.glide.GlideImage
import com.trendyol.vsh.interview.project.LocalNavController
import com.trendyol.vsh.interview.project.MainActivity
import com.trendyol.vsh.interview.project.R
import com.trendyol.vsh.interview.project.RouteDestinations
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem
import com.trendyol.vsh.interview.project.core.ui.model.ProductSlider
import com.trendyol.vsh.interview.project.theme.TrendyolTheme
import com.trendyol.vsh.interview.project.ui.section.ListingUiState
import com.trendyol.vsh.interview.project.ui.section.product.listing.ProductListingViewModel
import com.trendyol.vsh.interview.project.utils.PRODUCT_LISTING_IMAGE_ASPECT_RATIO
import dagger.hilt.android.EntryPointAccessors

@Composable
fun ProductSliderSection(
    productSlider: ProductSlider,
    modifier: Modifier,
) {

    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).productSliderViewModelFactory()

    val sliderViewModel: ProductListingViewModel = viewModel(
        factory = ProductSliderViewModel.provideFactory(
            factory,
            productsUrl = productSlider.fullServiceUrl
        )
    )
    val navController = LocalNavController.current
    val uiState by sliderViewModel.uiState.collectAsState()
    SliderStateHandler(
        uiState = uiState,
    ) { state ->
        SliderContent(
            products = state.products,
            modifier = modifier,
            onItemCLick = { productId -> navController.navigate("${RouteDestinations.PRODUCT_DETAILS_ROUTE}/$productId") },
        )
    }
}

@Composable
fun SliderContent(products: List<ProductItem>, onItemCLick: (Int) -> Unit, modifier: Modifier) {
    val itemWidth: Dp = itemWidth()

    val itemModifier = Modifier
        .width(itemWidth)

    LazyRow(modifier = modifier) {
        itemsIndexed(products) { index, product ->
            val startPadding = if (index > 0) 3.dp else 0.dp
            ProductSliderView(
                product = product,
                itemModifier
                    .padding(start = startPadding),
                itemWidth = itemWidth,
                onItemCLick = onItemCLick
            )
        }
    }
}

@Composable
private fun itemWidth(): Dp {
    val configuration = LocalConfiguration.current
    val columnsPerPage = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 4
        else -> 2
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp
    return (screenWidth / columnsPerPage).dp - (3.dp * columnsPerPage) - (screenWidth * 0.01f).dp
}

@Composable
fun ProductSliderView(
    product: ProductItem,
    modifier: Modifier,
    onItemCLick: (Int) -> Unit,
    itemWidth: Dp
) {
    val lineTitleHeight = MaterialTheme.typography.subtitle2.fontSize * 4 / 3

    Column(modifier = modifier.clickable(onClick = { onItemCLick(product.id) })) {
        GlideImage(
            imageModel = product.imageUrl,
            requestOptions = {
                RequestOptions()
                    .override(219, 328)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
            },
            modifier = modifier
                .width(itemWidth)
                .height(itemWidth / PRODUCT_LISTING_IMAGE_ASPECT_RATIO),
            placeHolder = painterResource(id = R.drawable.ic_placeholder_image_24)
        )
        Text(
            text = product.name,
            style = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.Bold, color = MaterialTheme.colors.primary
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .sizeIn(minHeight = with(LocalDensity.current) {
                    (lineTitleHeight * 3).toDp()
                })
                .padding(top = 6.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth(),
            lineHeight = lineTitleHeight
        )
        Text(
            text = product.salePrice ?: "",
            style = MaterialTheme.typography.subtitle2.copy(color = MaterialTheme.colors.secondary),
            maxLines = 1,
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
        )
    }
}

@Composable
private fun SliderStateHandler(
    uiState: ListingUiState<ProductItem>,
    content: @Composable (
        uiState: ListingUiState.Data<ProductItem>,
    ) -> Unit,
) {

    when (uiState) {
        is ListingUiState.Data -> {
            content(uiState)
        }
        is ListingUiState.Empty -> {
            if (uiState.errorMessages.isNotEmpty()) {
                Text(text = uiState.errorMessages.joinToString())
            } else {
                PlaceHolder()
            }
        }
    }
}

@Composable
private fun PlaceHolder() {
    val itemWidth: Dp = itemWidth()

    val minHeight = itemWidth / PRODUCT_LISTING_IMAGE_ASPECT_RATIO
    Row(modifier = Modifier.heightIn(min = minHeight)) {
        repeat(3) { i ->
            Box(
                modifier = Modifier
                    .heightIn(minHeight)
                    .width(itemWidth)
                    .padding(start = if (i > 0) 3.dp else 0.dp)
                    .background(color = TrendyolTheme.colors.uiBorder)
                    .placeholder(
                        visible = true,
                        color = TrendyolTheme.colors.placeHolder,
                        highlight = PlaceholderHighlight.shimmer(highlightColor = TrendyolTheme.colors.placeHolderHighlight),
                    ),
            )
        }
    }
}