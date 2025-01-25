package net.artificialwusslab.it_mirai_androidapp.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class MainPage: ComponentActivity() {
    @Composable
    fun UI(modifier: Modifier = Modifier){
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Text("Test!!!", modifier = Modifier.padding((innerPadding)))
        }
    }
}