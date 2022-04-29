package com.trendyol.vsh.interview.project.ui.section.product.detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.skydoves.landscapist.glide.GlideImage
import com.trendyol.vsh.interview.project.R
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem
import com.trendyol.vsh.interview.project.theme.TrendyolTheme
import com.trendyol.vsh.interview.project.ui.component.RatingBar
import com.trendyol.vsh.interview.project.utils.PRODUCT_LISTING_IMAGE_ASPECT_RATIO

private val TitleHeight = 128.dp
private val HzPadding = Modifier.padding(horizontal = 12.dp)

@Composable
fun ProductDetails(
    product: ProductItem?,
) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        ImageCarousel(product?.imageUrls ?: emptyList())
        Title(product)
        RatingBar(rating = product?.averageRating?.toFloat() ?: 0f, modifier = HzPadding.height(18.dp))
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun Title(product: ProductItem?) {

    val phModifier = HzPadding.placeholder(
        visible = product == null,
        color = TrendyolTheme.colors.placeHolder,
        highlight = PlaceholderHighlight.shimmer(highlightColor = TrendyolTheme.colors.placeHolderHighlight),
    )

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .fillMaxWidth()
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = product?.name ?: "",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = phModifier
                .widthIn(min = 200.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = product?.brandName ?: "",
            style = MaterialTheme.typography.subtitle2,
            color =  MaterialTheme.colors.secondary,
            modifier = phModifier
                .widthIn(min = 140.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = product?.salePrice ?: "",
            style = MaterialTheme.typography.subtitle1,
            color = TrendyolTheme.colors.brand,
            modifier = phModifier
                .widthIn(min = 80.dp)
        )

        Spacer(Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ImageCarousel(
    imageUrls: List<String>,
) {
    val halfWidth: Dp = LocalConfiguration.current.screenWidthDp.dp / 2
    val pagerState = rememberPagerState()
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(
            state = pagerState,
            count = imageUrls.size,
            modifier = Modifier
                .height(halfWidth / PRODUCT_LISTING_IMAGE_ASPECT_RATIO)
                .width(halfWidth)
                .placeholder(
                    visible = imageUrls.isEmpty(),
                    color = TrendyolTheme.colors.placeHolder,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = TrendyolTheme.colors.placeHolderHighlight),
                ),
        ) { index ->
            GlideImage(
                imageModel = imageUrls[index],
                contentScale = ContentScale.Crop,
                requestOptions = {
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerInside()
                },
                modifier = Modifier
                    .width(halfWidth)
                    .aspectRatio(PRODUCT_LISTING_IMAGE_ASPECT_RATIO),
                placeHolder = painterResource(id = R.drawable.ic_placeholder_image_24)
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp),
        )
    }
}
