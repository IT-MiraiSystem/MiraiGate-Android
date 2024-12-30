// TopPage.kt
package net.artificialwusslab.it_mirai_androidapp.Pages

import android.content.Context
import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.gms.common.SignInButton
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.launch
import net.artificialwusslab.it_mirai_androidapp.R

// 実際のパッケージ名に変更

class TopPage {
    @Composable
    fun UI(modifier: Modifier = Modifier, onLoginClick: (GetCredentialResponse) -> Unit = {}, onAfterAuth: (GetCredentialResponse) -> Unit) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val credentialManager = CredentialManager.create(context)
        val TAG = "ITMirai_TopPage"

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
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = 200.dp)
                ) {
                    Button(
                        onClick = {
                            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                                .setFilterByAuthorizedAccounts(true)
                                .setServerClientId(context.getString(R.string.default_web_client_id))
                                .setAutoSelectEnabled(true)
                                .build()
                            val credentialRequest: GetCredentialRequest = GetCredentialRequest.Builder()
                                .addCredentialOption(googleIdOption)
                                .build()
                            coroutineScope.launch {
                                try {
                                    val result = credentialManager.getCredential(
                                        request = credentialRequest,
                                        context = context,
                                    )
                                    //AccountService().onSignInGoogle(result, ComponentActivity())
                                    //AccountService().onSignInGoogle(result)
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
                        Image(painterResource(R.drawable.google_icon), contentDescription = "Googleでログイン",
                            modifier = Modifier.size(30.dp).padding(3.dp))
                        Text("Googleでログイン", fontSize = 16.sp, modifier = Modifier.padding(10.dp, 0.dp))
                    }
//                    AndroidView(
//                        factory = { ctx: Context ->
//                            SignInButton(ctx).apply {
//                                setSize(SignInButton.SIZE_WIDE)
//                                setOnClickListener { /*onLoginClick()*/ }
//                            }
//                        },
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                            .offset(y = 200.dp)
//                    )
                }
            }
        }
    }
}