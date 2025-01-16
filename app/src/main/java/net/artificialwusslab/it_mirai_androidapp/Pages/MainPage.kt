package net.artificialwusslab.it_mirai_androidapp.Pages

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
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
import net.artificialwusslab.it_mirai_androidapp.ui.theme.ITmiraiAndroidAppTheme

class MainPage: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ITmiraiAndroidAppTheme {
                UI()
            }
        }
    }

    @Composable
    fun UI(modifier: Modifier = Modifier){
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Text("Test!!!", modifier = Modifier.padding((innerPadding)))
        }
    }
}