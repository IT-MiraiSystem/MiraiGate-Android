package net.artificialwusslab.it_mirai_androidapp.ui

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.gson.Gson
import com.google.gson.JsonParser
import net.artificialwusslab.it_mirai_androidapp.AccountProfile
import net.artificialwusslab.it_mirai_androidapp.MiraiGate
import net.artificialwusslab.it_mirai_androidapp.R

class MainPage : ComponentActivity() {
    val TAG = "MiraiGate_MainPage"
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
                .offset(y = 100.dp)) {
                MainPageButton(painterResource(R.drawable.miraigate_icontp), cardText = "スケジュール", modifier = modifier)
                MainPageButton(painterResource(R.drawable.miraigate_topback), cardText = "カレンダー", modifier = modifier)
                MainPageButton(painterResource(R.drawable.ic_launcher), cardText = "ナビゲーション", modifier = modifier)
                MainPageButton(painterResource(R.drawable.ic_launcher_foreground), cardText = "図書室", modifier = modifier)
            }
            if(userInfoShowing) {
                AccountInfoDialog(userInfos = userInfos, onDismiss = { userInfoShowing = false }, modifier = modifier)
            }

        }
    }

    @Composable
    fun MainPageButton(painter: Painter, cardText: String = "カードテキスト", modifier: Modifier){
        Card(onClick = {
            println(cardText+"をクリックした")
        }, modifier = Modifier
            .padding(0.dp, 10.dp)
            .fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter, cardText, modifier = Modifier.size(80.dp))
                Column(modifier = Modifier.padding(20.dp, 0.dp)) {
                    Text(cardText, fontSize = 20.sp)
                }

            }

        }
    }

    @Composable
    fun AccountInfoDialog(userInfos: AccountProfile, onDismiss: () -> Unit, modifier: Modifier){
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp, 20.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
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
                    Text("ようこそ、${userInfos.name}さん", textAlign = TextAlign.Center, fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
                    Text("${userInfos.gradeInSchool}年${userInfos.classInSchool}組 ${userInfos.number}番", textAlign = TextAlign.Center, fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
                    Text(userInfos.email+"", textAlign = TextAlign.Center, fontSize = 13.sp)
                }

            }
        }
        //Text("ようこそ！\n${userInfos.gradeInSchool}年${userInfos.classInSchool}組${userInfos.name}さん！")
    }
}