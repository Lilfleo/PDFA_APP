package com.pdfa.pdfa_app.user_interface.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.user_interface.rooting.NavigationItem
import com.pdfa.pdfa_app.user_interface.rooting.painter

@Composable
fun BottomNavBar(
    selectedIndex: Int,
    onItemSelected: (Int, String) -> Unit,
    items: List<NavigationItem>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color(0xFFF4EEE1)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isSelected) Color(0xFFECE6DA) else Color.Transparent)
                    .clickable { onItemSelected(index, item.route) }
                    .padding(12.dp)
            ) {
                Icon(
                    painter = item.icon?.painter() ?: return@Box,
                    contentDescription = item.title,
                    tint = if (isSelected) Color.Black else Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
            }


            if (index != items.lastIndex) {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .padding(horizontal = 4.dp)
                        .width(1.dp)
                        .background(Color(0xFFCCC5B9))
                )
            }
        }
    }

}
