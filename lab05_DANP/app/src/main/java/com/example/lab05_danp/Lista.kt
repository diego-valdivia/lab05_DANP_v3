package com.example.lab05_danp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ListScreen(navController: NavController) {
    val items = listOf(
        ListItemData("2023-08-09", "15:30", "High"),
        ListItemData("2023-08-10", "09:45", "Medium"),
        ListItemData("2023-08-11", "18:15", "Low")
    )

    LazyColumn {
        items(items) { item ->
            ListItem(item, navController)
        }
    }
}

data class ListItemData(val date: String, val time: String, val intensity: String)

@Composable
fun ListItem(item: ListItemData, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Navigate to detail screen or perform other action
                navController.navigate("detail/${item.date}")
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Date: ${item.date}",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Time: ${item.time}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Intensity: ${item.intensity}")
        }
    }
}
