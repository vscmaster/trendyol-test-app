package com.trendyol.vsh.interview.project.ui.section.product.listing

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.skydoves.landscapist.glide.GlideImage
import com.trendyol.vsh.interview.project.LocalNavController
import com.trendyol.vsh.interview.project.MainActivity
import com.trendyol.vsh.interview.project.R
import com.trendyol.vsh.interview.project.RouteDestinations
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem
import com.trendyol.vsh.interview.project.core.ui.model.ProductListing
import com.trendyol.vsh.interview.project.theme.TrendyolTheme
import com.trendyol.vsh.interview.project.ui.section.ListingUiState
import com.trendyol.vsh.interview.project.utils.PRODUCT_LISTING_IMAGE_ASPECT_RATIO
import dagger.hilt.android.EntryPointAccessors

private const val TAG = "ProductListingSection"

@Composable
fun ProductListingSection(
    productListing: ProductListing,
) {

    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).productListingViewModelFactory()

    val listingViewModel: ProductListingViewModel = viewModel(
        factory = ProductListingViewModel.provideFactory(
            factory,
            productsUrl = productListing.fullServiceUrl
        )
    )

    val navController = LocalNavController.current
    val uiState by listingViewModel.uiState.collectAsState()
    ListingStateHandler(
        uiState = uiState,
    ) { state ->
        ListingContent(
            items = state.products,
            onItemCLick = { productId -> navController.navigate("${RouteDestinations.PRODUCT_DETAILS_ROUTE}/$productId") },
        )
    }
}

@Composable
fun ListingContent(items: List<ProductItem>, onItemCLick: (Int) -> Unit) {
    val configuration = LocalConfiguration.current
    val columnsPerPage = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 4
        else -> 2
    }
    val itemSize: Dp =
        LocalConfiguration.current.screenWidthDp.dp / columnsPerPage - 2.dp * columnsPerPage
    val itemModifier = Modifier
        .width(itemSize)
        .padding(start = 2.dp, end = 2.dp)
    FlowRow(
        modifier = Modifier.padding(top = 12.dp),
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = 8.dp,
        mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly
    ) {
        items.forEachIndexed { index, _ ->
            ProductView(items[index], modifier = itemModifier, onItemCLick = onItemCLick)
        }
    }
}

@Composable
fun ProductView(product: ProductItem, modifier: Modifier, onItemCLick: (Int) -> Unit) {
    Card(
        modifier = modifier
            .clickable(onClick = { onItemCLick(product.id) })
    ) {
        Column {
            GlideImage(
                imageModel = product.imageUrl,
                contentScale = ContentScale.Crop,
                requestOptions = {
                    RequestOptions()
                        .override(219, 328)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerInside()
                },
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(PRODUCT_LISTING_IMAGE_ASPECT_RATIO),
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
                    .padding(top = 12.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
            )
            Text(
                text = product.brandName ?: "",
                style = MaterialTheme.typography.subtitle2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 3.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
            )
            Text(
                text = product.salePrice ?: "",
                style = MaterialTheme.typography.subtitle2.copy(color = MaterialTheme.colors.secondary),
                maxLines = 1,
                modifier = Modifier
                    .padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun ListingStateHandler(
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
    val itemWidth: Dp = (LocalConfiguration.current.screenWidthDp / 2f).dp - 6.dp
    val minHeight = itemWidth / PRODUCT_LISTING_IMAGE_ASPECT_RATIO
    Row(
        modifier = Modifier
            .heightIn(min = minHeight)
            .padding(top = 12.dp)
    ) {
        repeat(2) {
            Card(
                modifier = Modifier
                    .padding(start = 3.dp, end = 3.dp)
                    .width(itemWidth),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(minHeight)
                        .background(color = TrendyolTheme.colors.placeHolder)
                        .padding(4.dp)
                        .placeholder(
                            visible = true,
                            color = TrendyolTheme.colors.placeHolder,
                            highlight = PlaceholderHighlight.shimmer(highlightColor = TrendyolTheme.colors.placeHolderHighlight),
                        )
                )
            }
        }
    }
}