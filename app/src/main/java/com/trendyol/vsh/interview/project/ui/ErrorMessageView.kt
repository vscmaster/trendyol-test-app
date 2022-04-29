package com.trendyol.vsh.interview.project.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.trendyol.vsh.interview.project.core.ui.model.ErrorMessage
import com.trendyol.vsh.interview.project.R

@Composable
fun RetryMessage(
    errorMessages: List<ErrorMessage>,
    onRetry: () -> Unit,
    cancelable: Boolean = false,
    isVisible: Boolean,
    onCancel: () -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            initialAlpha = 0.5f,
            animationSpec = tween(300)
        ),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        //val errorMessage = remember(uiState) { uiState.errorMessages[0] }
        val interactionSource = remember { MutableInteractionSource() }
        val errorMessageText: String = stringResource(errorMessages[0].messageId)
        val retryMessageText = stringResource(id = R.string.retry)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White.copy(alpha = 0.95f))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {},
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = errorMessageText,
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Center
                )
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .defaultMinSize(minWidth = 200.dp),
                    onClick = {
                        onCancel()
                        onRetry()
                    },
                    // Uses ButtonDefaults.ContentPadding by default
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    )
                ) {
                    Text(text = retryMessageText)
                }
                if (cancelable) {
                    val cancelText = stringResource(id = R.string.cancel)
                    Button(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .defaultMinSize(minWidth = 200.dp),
                        onClick = { onCancel() },
                        // Uses ButtonDefaults.ContentPadding by default
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            top = 12.dp,
                            end = 20.dp,
                            bottom = 12.dp
                        )
                    ) {
                        Text(text = cancelText)
                    }
                }
            }
        }
    }
}