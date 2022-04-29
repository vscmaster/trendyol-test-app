package com.trendyol.vsh.interview.project.ui.section.banner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.trendyol.vsh.interview.project.core.ui.model.SingleBanner

@Composable
fun BannerSection(
    banner: SingleBanner,
    modifier: Modifier,
    onBannerClick: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .heightIn(min = 240.dp)
            .clickable(onClick = { onBannerClick(banner.bannerEventKey) }),
        contentAlignment = Alignment.BottomStart
    ) {
        GlideImage(
            imageModel = banner.imageUrl,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = banner.title,
            style = MaterialTheme.typography.h4.copy(
                color = MaterialTheme.colors.secondary,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = MaterialTheme.colors.onPrimary,
                    offset = Offset(4f, 4f),
                    blurRadius = 8f
                )
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .fillMaxWidth()
        )
    }
}