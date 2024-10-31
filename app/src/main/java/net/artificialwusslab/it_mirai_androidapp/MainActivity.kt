package net.artificialwusslab.it_mirai_androidapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import net.artificialwusslab.it_mirai_androidapp.ui.theme.ITmiraiAndroidAppTheme

class MainActivity : ComponentActivity() {
    private var auth: FirebaseAuth? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    private var TAG = "I活Info"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .setHostedDomain("it-mirai-h.ibk.ed.jp")
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        window.statusBarColor = Color.Black.toArgb()
        if(auth?.currentUser != null){
            //メイン画面を表示する
            setContent {
                ITmiraiAndroidAppTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }else{
            //トップ画面を表示する
            setContent {
                ITmiraiAndroidAppTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        TopPage(
                            modifier = Modifier.padding(innerPadding)
                            ,onLoginClick = { signIn() }
                        )
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                // Sign-in succeeded, navigate to the main screen
                setContent {
                    ITmiraiAndroidAppTheme {
                        val navController = rememberNavController()
                        //アカウントのセットアップ画面を表示する
                    }
                }
            } catch (e: ApiException) {
                // Sign-in failed, handle the error
            }
        }
    }
    //Googleアカウントでログイン
    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        auth?.signOut()
    }

}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ITmiraiAndroidAppTheme {
        Greeting("Android")
    }
}

@Composable
fun TopPage(modifier: Modifier = Modifier, onLoginClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize() // modifierを使う
    ) {
        SubcomposeAsyncImage(
            model = "https://www.it-mirai-h.ibk.ed.jp/wysiwyg/image/download/1/325/",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x99EEEEEE))
        ) {
            Text(
                text = "Welcome to ITmirai",
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
            Button(
                onClick = onLoginClick,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text("Login")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopPagePreview() {
    MaterialTheme {
        TopPage(onLoginClick = {})
    }
}