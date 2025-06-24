package com.example.pdfa_app

import FoodScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdfa_app.ui.theme.PDFA_APPTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.ViewModel
import com.example.pdfa_app.ui.viewmodel.FoodViewModel
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ViewModelComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        //    PDFA_APPTheme {
            //      Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            //      Greeting(
            //          name = "Android",
            //          modifier = Modifier.padding(innerPadding)
            //      )
            //  }
            PDFA_APPTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), content = { innerPadding ->
                    FoodScreen(Modifier.padding(innerPadding))
                })
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PDFA_APPTheme {
        Greeting("Android")
    }
}