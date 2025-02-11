package net.artificialwusslab.it_mirai_androidapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonParser
import net.artificialwusslab.it_mirai_androidapp.AccountProfile
import net.artificialwusslab.it_mirai_androidapp.DialogWrapper
import net.artificialwusslab.it_mirai_androidapp.MainActivity
import net.artificialwusslab.it_mirai_androidapp.MiraiGate
import net.artificialwusslab.it_mirai_androidapp.R
import net.artificialwusslab.it_mirai_androidapp.ui.theme.ITmiraiAndroidAppTheme

class MainPage : ComponentActivity() {
    val TAG = "MiraiGate_MainPage"
    val auth: FirebaseAuth = Firebase.auth

    @Composable
    fun UI(modifier: Modifier = Modifier, myProfile: List<String> = listOf()) {
        val userInfos = Gson().fromJson(myProfile[0], AccountProfile::class.java)
        var userInfoShowing by remember{ mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFF00))
        ) {
            Image(
                painter = painterResource(id = R.drawable.miraigate_topback),
                contentDescription = "背景",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Row(modifier = Modifier.padding(20.dp, 10.dp)) {
                Text(
                    "MiraiGate",
                    modifier = modifier,
                    fontFamily = FontFamily(Font(R.font.novaround)),
                    color = Color.White,
                    fontSize = 32.sp
                )
                Spacer(modifier = modifier.weight(1f))
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "設定",
                    tint = Color.White,
                    modifier = modifier
                        .size(35.dp)
                        .clickable(onClickLabel = "設定") {
                            userInfoShowing = true
                        },
                )
                Spacer(modifier = modifier.padding(start = 10.dp))
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(userInfos.photoURL)
                        .crossfade(true)
                        .placeholder(R.drawable.outline_account_circle_24)
                        .error(R.drawable.outline_account_circle_24)
                        .transformations(CircleCropTransformation())
                        .build(),
                    contentDescription = "ユーザアイコン",
                    modifier = modifier
                        .size(35.dp)
                        .clickable {
                            userInfoShowing = true
                        },
                )
            }


            Column(modifier = modifier
                .padding(25.dp)
                .offset(y = 50.dp)) {
                WentSchoolText(userInfos = userInfos, modifier = modifier)
                MainPageButton(painterResource(R.drawable.ic_fluent_clipboard_text), cardText = "時間割の確認", modifier = modifier)
                MainPageButton(painterResource(R.drawable.ic_fluent_power), cardText = "PCの電源操作", modifier = modifier)
                MainPageButton(painterResource(R.drawable.ic_fluent_tasks), cardText = "課題の管理", modifier = modifier)
                MainPageButton(painterResource(R.drawable.ic_fluent_calendar), cardText = "カレンダー", modifier = modifier)
            }
            if(userInfoShowing) {
                AccountInfoDialog(userInfos = userInfos, onDismiss = { userInfoShowing = false }, modifier = modifier)
            }

        }
    }

    @Composable
    fun MainPageButton(painter: Painter, cardText: String = "カードテキスト", modifier: Modifier){
        Card(
            onClick = {
                println(cardText+"をクリックした")
            },
            modifier = Modifier
                .padding(0.dp, 10.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(255, 255, 255, 128))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter, cardText, modifier = Modifier.size(80.dp).padding(10.dp))
                Column(modifier = Modifier.padding(20.dp, 0.dp)) {
                    Text(cardText, fontSize = 20.sp)
                }

            }

        }
    }

    @Composable
    fun WentSchoolText(userInfos: AccountProfile, modifier: Modifier = Modifier){
        var wentSchool by remember { mutableStateOf(true) }
        wentSchool = false
        Card(
            modifier = Modifier
                .padding(0.dp, 10.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(255, 255, 255, 128))
        ) {
            if(wentSchool) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(10.dp, 20.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painterResource(R.drawable.ic_fluent_checkmark),
                        contentDescription = "完了",
                        modifier = Modifier.size(60.dp)
                    )
                    Text(
                        "登校しました",
                        fontSize = 28.sp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text("YYYY年M月D日 HH時mm分")
                }
            }
            else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(10.dp, 20.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painterResource(R.drawable.ic_fluent_circle_small),
                        contentDescription = "未完了",
                        modifier = Modifier.size(60.dp)
                    )
                    Text(
                        "まだ登校していません",
                        fontSize = 28.sp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text("YYYY年M月D日")
                }
            }
        }
    }

    @Composable
    fun AccountInfoDialog(userInfos: AccountProfile, onDismiss: () -> Unit, modifier: Modifier){
        var logoutClicked by remember { mutableStateOf(false) }
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "dummy") {
            composable("dummy"){

            }
            composable("LogoutDialog"){
                DialogWrapper().Logout(onConfirmation = {
                    auth.signOut()
                    setContent {
                        ITmiraiAndroidAppTheme {
                            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                Text("aaaa", modifier = Modifier.padding(innerPadding))
                            }
                        }
                    }
                })
            }
        }
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(
                modifier = modifier
                    .fillMaxWidth(),
                    //.height(300.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box {
                    IconButton(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "閉じる",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(10.dp, 20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(userInfos.photoURL)
                                .crossfade(true)
                                .placeholder(R.drawable.outline_account_circle_24)
                                .error(R.drawable.outline_account_circle_24)
                                .transformations(CircleCropTransformation())
                                .build(),
                            contentDescription = "ユーザアイコン",
                            modifier = Modifier
                                .size(50.dp)
                        )
                        Spacer(modifier = Modifier.padding(bottom = 20.dp))
                        Text(
                            "ようこそ、${userInfos.name}さん",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            "${userInfos.gradeInSchool}年${userInfos.classInSchool}組 ${userInfos.number}番",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(userInfos.email + "", textAlign = TextAlign.Center, fontSize = 13.sp)
                        Spacer(modifier = Modifier.padding(bottom = 20.dp))
                        TextButton(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp)
                        ) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = "設定",
                                modifier = Modifier.padding(end = 5.dp)
                            )
                            Text("MiraiGateの設定", fontSize = 15.sp)
                        }
                        TextButton(
                            onClick = {
                                //navController.navigate("LogoutDialog")
                                //logoutClicked = true
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                Icons.AutoMirrored.Default.ExitToApp,
                                contentDescription = "設定",
                                modifier = Modifier.padding(end = 5.dp)
                            )
                            Text("ログアウト", fontSize = 15.sp)
                        }
                    }
                }

            }
        }
        if(logoutClicked){
            DialogWrapper().Logout(onConfirmation = {
                auth.signOut()
                setContent {
                    ITmiraiAndroidAppTheme {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Text("aaaa", modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            })
        }
    }
}