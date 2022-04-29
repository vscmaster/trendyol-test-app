package com.trendyol.vsh.interview.project.ui.section.banner

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage
import com.trendyol.vsh.interview.project.R
import com.trendyol.vsh.interview.project.core.ui.model.CarouselBanner
import com.trendyol.vsh.interview.project.core.ui.model.CarouselBannerContent

@Composable
fun CarouselBannerSection(
    banner: CarouselBanner,
    modifier: Modifier,
    onBannerCLick: (String) -> Unit,

    ) {
    val configuration = LocalConfiguration.current
    val columnsPerPage = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> banner.displayCount * 2
        else -> banner.displayCount
    }
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val itemWidth: Dp =
        (screenWidth / columnsPerPage).dp - if (columnsPerPage < banner.carouselBannerContents.size) (screenWidth * 0.01).dp else 0.dp

    val itemModifier = Modifier
        .width(itemWidth)
        .padding(3.dp)

    LazyRow(modifier = modifier) {
        items(banner.carouselBannerContents) { content ->
            BannerContent(
                content = content,
                modifier = itemModifier,
                itemWidth = itemWidth,
                onItemCLick = { id -> onBannerCLick(id.toString()) }
            )
        }
    }
}

@Composable
fun BannerContent(
    content: CarouselBannerContent,
    modifier: Modifier,
    onItemCLick: (Int) -> Unit,
    itemWidth: Dp
) {
    val textPadding = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp)

    Card(
        modifier = modifier.clickable(onClick = { onItemCLick(content.id) })
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GlideImage(
                imageModel = content.imageUrl,
                requestOptions = {
                    RequestOptions()
                        .override(219, 328)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                },
                modifier = modifier
                    .height(itemWidth - 14.dp)
                    .clip(CircleShape),
                placeHolder = painterResource(id = R.drawable.ic_placeholder_image_24)
            )
            Text(
                text = content.title,
                style = MaterialTheme.typography.subtitle2.copy(fontSize = 9.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = textPadding.padding(top = 4.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.discover_now),
                style = MaterialTheme.typography.subtitle2.copy(
                    fontSize = 9.sp,
                    textDecoration = TextDecoration.Underline
                ),
                maxLines = 1,
                modifier = textPadding.padding(bottom = 10.dp)
            )
        }
    }
}