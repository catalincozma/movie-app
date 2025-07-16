package com.catalincozma.ui.screen.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.catalincozma.model.RecommendationType

@Composable
fun RecommendationTypeTabs(
    selectedType: RecommendationType,
    onTypeSelected: (RecommendationType) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        RecommendationType.entries.forEach { type ->
            val isSelected = type == selectedType
            Text(
                text = type.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() },
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onTypeSelected(type) },
                color = if (isSelected) Color.Blue else Color.Gray
            )
        }
    }
}