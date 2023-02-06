package com.example.foodapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.foodapplication.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemCardTitLe(
    title: String,
    key: String,
    modifier: Modifier = Modifier,
    navigateToListDetail: (String) -> Unit
) {
    Card (
        shape = RoundedCornerShape(5.dp),
        modifier = modifier.fillMaxWidth(),
        elevation = 5.dp,
        onClick = { navigateToListDetail(key) }
    ) {
        Row (modifier = modifier
                .padding(vertical = 10.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "icon detail",
                tint = Color(
                    ContextCompat.getColor(LocalContext.current, R.color.young_red)
                )
            )
        }
    }
}

@Preview
@Composable
fun ItemCardTitlePrev() {
    MaterialTheme {
        ItemCardTitLe("Hello World", key="ada", navigateToListDetail = {})
    }
}