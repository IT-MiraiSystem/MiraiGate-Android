package net.artificialwusslab.it_mirai_androidapp.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alorma.compose.settings.ui.SettingsGroup
import com.alorma.compose.settings.ui.SettingsMenuLink
import net.artificialwusslab.it_mirai_androidapp.R

class SettingsPage {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UI(modifier: Modifier = Modifier, miraiGateNavController: NavHostController){
        var navController = rememberNavController()
        NavHost(navController = navController, startDestination = "dummy"){
            composable("dummy"){

            }
        }
        Scaffold(modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text("設定")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            Log.d("MiraiGate_SettingsPage", "Unkonow")
                            if (miraiGateNavController.previousBackStackEntry != null) {
                                miraiGateNavController.popBackStack()
                            }
                        }) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "戻る")
                        }

                    }
                )
            }) { innerPadding ->
            Box(modifier = modifier.padding(innerPadding)) {
                Column(modifier = Modifier.padding(10.dp, 0.dp).verticalScroll(rememberScrollState())) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.ic_launcher_round),
                            contentDescription = "ロゴ"
                        )
                        Column {
                            Text(
                                "MiraiGate",
                                modifier = modifier.padding(10.dp, 0.dp),
                                fontFamily = FontFamily(Font(R.font.novaround)),
                                fontSize = 30.sp
                            )
                            Text(
                                "Ver.0.1.0.0",
                                modifier = modifier.padding(10.dp, 0.dp),
                                fontSize = 16.sp
                            )
                        }
                    }
                    HorizontalDivider(thickness = 3.dp, modifier = Modifier.padding(15.dp))
                    Column(modifier = modifier.padding(0.dp, 10.dp)) {
                        Text("学校機能の設定", fontSize = 22.sp, modifier = modifier.padding(bottom = 10.dp))
                        SettingsMenuLink(
                            modifier = modifier,
                            icon = {
                                Image(
                                    painter = painterResource(R.drawable.ic_fluent_checkmark),
                                    contentDescription = "登校",
                                    modifier = Modifier.size(50.dp)
                                )
                            },
                            title = { Text("登校チェック") },
                            onClick = {}
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(15.dp, 0.dp)
                        )
                        SettingsMenuLink(
                            modifier = modifier,
                            icon = {
                                Image(
                                    painter = painterResource(R.drawable.ic_fluent_clipboard_text),
                                    contentDescription = "時間割",
                                    modifier = Modifier.size(50.dp)
                                )
                            },
                            title = { Text("時間割の確認") },
                            onClick = {}
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(15.dp, 0.dp)
                        )
                        SettingsMenuLink(
                            modifier = modifier,
                            icon = {
                                Image(
                                    painter = painterResource(R.drawable.ic_fluent_power),
                                    contentDescription = "電源",
                                    modifier = Modifier.size(50.dp)
                                )
                            },
                            title = { Text("PCの電源操作") },
                            onClick = {}
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(15.dp, 0.dp)
                        )
                        SettingsMenuLink(
                            modifier = modifier,
                            icon = {
                                Image(
                                    painter = painterResource(R.drawable.ic_fluent_tasks),
                                    contentDescription = "課題",
                                    modifier = Modifier.size(50.dp)
                                )
                            },
                            title = { Text("課題の管理") },
                            onClick = {}
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(15.dp, 0.dp)
                        )
                        SettingsMenuLink(
                            modifier = modifier,
                            icon = {
                                Image(
                                    painter = painterResource(R.drawable.ic_fluent_calendar),
                                    contentDescription = "カレンダー",
                                    modifier = Modifier.size(50.dp)
                                )
                            },
                            title = { Text("カレンダー") },
                            onClick = {}
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(15.dp, 0.dp)
                        )
                        Text("アプリの設定", fontSize = 22.sp, modifier = modifier.padding(top = 30.dp, bottom = 10.dp))
                        SettingsMenuLink(
                            modifier = modifier,
                            icon = {
                                Icon(Icons.Default.AccountCircle, contentDescription = "アカウント")
                            },
                            title = { Text("アカウント") },
                            onClick = {}
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(15.dp, 0.dp)
                        )
                        SettingsMenuLink(
                            modifier = modifier,
                            icon = {
                                Icon(Icons.Default.Notifications, contentDescription = "通知")
                            },
                            title = { Text("プッシュ通知") },
                            onClick = {}
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(15.dp, 0.dp)
                        )
                        Text("MiraiGateについて", fontSize = 22.sp, modifier = modifier.padding(top = 30.dp, bottom = 10.dp))
                        SettingsMenuLink(
                            modifier = modifier,
                            title = { Text("利用規約") },
                            onClick = {}
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(15.dp, 0.dp)
                        )
                        SettingsMenuLink(
                            modifier = modifier,
                            title = { Text("プライバシーポリシー") },
                            onClick = {}
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(15.dp, 0.dp)
                        )
                        SettingsMenuLink(
                            modifier = modifier,
                            title = { Text("お問い合わせ") },
                            onClick = {}
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(15.dp, 0.dp)
                        )
                        SettingsMenuLink(
                            modifier = modifier,
                            title = { Text("オープンソースライセンス") },
                            onClick = { miraiGateNavController.navigate("Settings/About/OpenLicenses") }
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(15.dp, 0.dp)
                        )
                    }
                    Text(
                        "© やる気はお家でオンライン",
                        modifier = modifier.padding(0.dp, 10.dp).align(Alignment.CenterHorizontally),
                        color = Color.Black,
                        fontSize = 16.sp
                    )

                }
                Log.d("gaer", "Fuck you!")
            }
        }
    }
}