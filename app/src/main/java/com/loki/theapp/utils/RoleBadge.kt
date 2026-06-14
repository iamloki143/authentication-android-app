package com.loki.theapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authapp.ui.theme.Amber50
import com.example.authapp.ui.theme.Amber800
import com.example.authapp.ui.theme.Teal50
import com.example.authapp.ui.theme.Teal600

@Composable
fun RoleBadge(role: String) {
    val (bg, fg) = when (role.lowercase()) {
        "admin" -> Amber50  to Amber800
        else    -> Teal50   to Teal600
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(role, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = fg)
    }
}
