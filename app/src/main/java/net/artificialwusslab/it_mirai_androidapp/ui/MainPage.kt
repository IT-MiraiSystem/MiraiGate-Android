package net.artificialwusslab.it_mirai_androidapp.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.artificialwusslab.it_mirai_androidapp.MiraiGate
import net.artificialwusslab.it_mirai_androidapp.R

class MainPage : ComponentActivity() {
    @Composable
    fun UI(modifier: Modifier = Modifier) {
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
            Text(
                "MiraiGate",
                modifier = modifier
                    .padding(10.dp)
                    .align(Alignment.TopCenter),
                fontFamily = FontFamily(Font(R.font.novaround)),
                color = Color.White,
                fontSize = 32.sp
            )

            Column(modifier = modifier.padding(25.dp).offset(y = 100.dp)) {
                MainPageButton(painterResource(R.drawable.miraigate_icontp), cardText = "スケジュール", modifier = modifier)
                MainPageButton(painterResource(R.drawable.miraigate_topback), cardText = "カレンダー", modifier = modifier)
                MainPageButton(painterResource(R.drawable.ic_launcher), cardText = "ナビゲーション", modifier = modifier)
                MainPageButton(painterResource(R.drawable.ic_launcher_foreground), cardText = "図書室", modifier = modifier)
            }

        }
    }

    @Composable
    fun MainPageButton(painter: Painter, cardText: String = "カードテキスト", modifier: Modifier){
        Card(onClick = {
            println(cardText+"をクリックした")
        }, modifier = Modifier.padding(0.dp, 10.dp).fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter, cardText, modifier = Modifier.size(80.dp))
                Column(modifier = Modifier.padding(20.dp, 0.dp)) {
                    Text(cardText, fontSize = 20.sp)
                }

            }

        }
    }
}