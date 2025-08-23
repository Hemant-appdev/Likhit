package com.hbworld.likhit.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun CenteredTransparentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier
) {

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        // Set the text style for the actual input text
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = fontSize
        ),
        // Use a solid color cursor that will be visible on your background
        cursorBrush = SolidColor(LocalContentColor.current),
        decorationBox = { innerTextField ->
            // The decorationBox allows us to place the placeholder and the input field
            // in the same layout area.
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center // This centers everything inside the Box
            ) {
                // If the text is empty, show the placeholder
                if (value.isEmpty()) {
                    Text(
                        text = placeholderText,
                        textAlign = TextAlign.Center,
                        color = Color.Black.copy(alpha = 0.5f), // Use a semi-transparent color for placeholder
                        fontSize = fontSize
                    )
                }
                // This is the actual, core text field
                innerTextField()
            }
        }
    )
}

@Composable
@Preview
fun PreviewCenteredTransparentTextField() {
    CenteredTransparentTextField(
        value = "",
        onValueChange = {},
        fontSize = 32.sp,
        placeholderText = "Enter title",
    )
}