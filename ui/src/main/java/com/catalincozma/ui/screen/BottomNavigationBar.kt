package com.catalincozma.ui.screen

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.res.stringResource

@Composable
fun BottomNavigationBar(
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    NavigationBar {
        BottomNavItem.entries.forEach { item ->
            NavigationBarItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = stringResource(id = item.labelResId),
                            modifier = Modifier.size(24.dp)
                        )
                        if (item == selectedItem) {
                            Text(
                                text = stringResource(id = item.labelResId),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                },
                selected = item == selectedItem,
                onClick = { onItemSelected(item) }
            )
        }
    }
}
