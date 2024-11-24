// TopPage.kt
package com.example.yourapp // 実際のパッケージ名に変更

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.common.SignInButton
import net.artificialwusslab.it_mirai_androidapp.R

@Composable
fun TopPage(modifier: Modifier = Modifier, onLoginClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF19b0e7))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher),
                    contentDescription = "アプリアイコン",
                    modifier = Modifier
                        .size(256.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "ようこそIT未来高校へ",
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                AndroidView(
                    factory = { ctx: Context ->
                        SignInButton(ctx).apply {
                            setSize(SignInButton.SIZE_WIDE)
                            setOnClickListener { onLoginClick() }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 200.dp)
                )
            }
        }
    }
}
