// TopPage.kt
package net.artificialwusslab.it_mirai_androidapp.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.launch
import net.artificialwusslab.it_mirai_androidapp.R

// 実際のパッケージ名に変更

class TopPage {

    @Composable
    fun UI(
        modifier: Modifier = Modifier,
        onLoginClick: (GetCredentialResponse) -> Unit = {},
        onAfterAuth: (GetCredentialResponse) -> Unit = {},
        loginSuccess: Int = 0
    ) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val credentialManager = CredentialManager.create(context)
        val TAG = "MiraiGate_TopPage"

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0C57F3))
        ) {
            Image(
                painter = painterResource(id = R.drawable.miraigate_topback),
                contentDescription = "背景",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(50.dp, 100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.miraigate_icontp),
                    contentDescription = "アプリアイコン",
                    modifier = Modifier
                        .size(256.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "Welcome to MiraiGate",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontFamily = FontFamily(Font(resId = R.font.novaround)),
                    fontSize = 25.sp
                )
                //Spacer(modifier = Modifier.height(50.dp))
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 200.dp)
            ) {
                var loginScs by remember { mutableStateOf(0) }
                loginScs = loginSuccess
                if(loginScs == 1) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier)
                }
                else if(loginScs == 2){
                    return
                }
                else if(loginScs == 3){
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        enabled = false
                    ) {
                        Image(
                            painterResource(R.drawable.google_icon),
                            contentDescription = "Googleでログイン",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(3.dp)
                        )
                        Text(
                            "Googleでログイン",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(10.dp, 0.dp)
                        )
                    }
                }
                else {
                    Button(
                        onClick = {
                            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                                .setFilterByAuthorizedAccounts(true)
                                .setServerClientId(context.getString(R.string.default_web_client_id))
                                .setAutoSelectEnabled(true)
                                .build()
                            val credentialRequest: GetCredentialRequest =
                                GetCredentialRequest.Builder()
                                    .addCredentialOption(googleIdOption)
                                    .build()
                            coroutineScope.launch {
                                try {
                                    val result = credentialManager.getCredential(
                                        request = credentialRequest,
                                        context = context,
                                    )
                                    onAfterAuth(result)
                                } catch (e: GetCredentialException) {
                                    Log.e(TAG, "Credential Error.")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Image(
                            painterResource(R.drawable.google_icon),
                            contentDescription = "Googleでログイン",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(3.dp)
                        )
                        Text(
                            "Googleでログイン",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(10.dp, 0.dp)
                        )
                    }
                }
            }
        }
    }
}