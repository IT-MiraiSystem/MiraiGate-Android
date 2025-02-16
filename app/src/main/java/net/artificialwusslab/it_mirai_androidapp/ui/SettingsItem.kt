package net.artificialwusslab.it_mirai_androidapp.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer

class SettingsItem {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun OpenLicenses(modifier: Modifier = Modifier, miraiGateNavController: NavHostController) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text("オープンソースライセンス")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (miraiGateNavController.previousBackStackEntry != null) {
                                miraiGateNavController.popBackStack()
                            }
                        }) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "戻る")
                        }

                    }
                )
            }
        ) { innerPadding ->
            LibrariesContainer(
                modifier = Modifier.padding(innerPadding).fillMaxSize()
            )
        }


    }
}