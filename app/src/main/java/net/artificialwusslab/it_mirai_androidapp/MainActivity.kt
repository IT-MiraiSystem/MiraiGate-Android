package net.artificialwusslab.it_mirai_androidapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import net.artificialwusslab.it_mirai_androidapp.ui.theme.ITmiraiAndroidAppTheme

class MainActivity : ComponentActivity() {
    private var auth: FirebaseAuth? = null
    private var TAG = "I活Info"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = Firebase.auth
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
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
    private fun signIn(email: String, password: String) {

        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    //メイン画面に移行する
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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
fun TopPage(modifier: Modifier = Modifier){
    //中にページの構成を作成する
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = "https://example.com/your-image-url.jpg",
            contentDescription = null,
            modifier = Modifier
                .size(200.dp) // Adjust the size as needed
                .border(2.dp, Color(0xEEEEEE9A))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopPagePreview() {
    ITmiraiAndroidAppTheme {
        TopPage()
    }
}