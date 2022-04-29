package com.trendyol.vsh.interview.project.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppBar(
    elevation: Dp,
    title: String
) {
    androidx.compose.material.TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        },
        elevation = elevation
    )
}