package com.pdfa.pdfa_app



import MainScreen
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun App() {
    Surface(color = MaterialTheme.colorScheme.background) {
        MainScreen()
    }
}
