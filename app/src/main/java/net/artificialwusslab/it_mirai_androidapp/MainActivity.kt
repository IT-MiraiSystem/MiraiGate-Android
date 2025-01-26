package net.artificialwusslab.it_mirai_androidapp


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.gson.JsonParser
import net.artificialwusslab.it_mirai_androidapp.ui.TopPage
import net.artificialwusslab.it_mirai_androidapp.ui.theme.ITmiraiAndroidAppTheme

class MainActivity : ComponentActivity() {
    private var auth: FirebaseAuth? = null
    val TAG: String = "MainActivity"
    private var Access_Token: String? = null
    var User: HashMap<String, String?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = Firebase.auth
        //Access_Token = JsonParser.parseString(AuthDeviceReq[0]).asJsonObject.get("token").asString
        Log.i(TAG, "Access_Token: $Access_Token")
//        Log.i(TAG, AccountService().GetFirebaseToken().toString())
        Log.i(TAG, AccountService().GetFirebaseToken().toString())
        if (auth?.currentUser != null) {
            //メイン画面を表示する
            Log.i(TAG, auth?.currentUser.toString())
            auth?.currentUser?.getIdToken(true)
                ?.addOnCompleteListener { task ->
                    var tokens = API.get(APIPath.signIn, hashMapOf(), task.result.token)[0]
                    println(task.result.token)
                    setContent {
                        ITmiraiAndroidAppTheme {
                            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                MiraiGate().StartPage(
                                    "Main",
                                    modifier = Modifier.padding(innerPadding),
                                    myProfile = tokens
                                )
                            }
                        }
                    }
                }
            setContent {
                ITmiraiAndroidAppTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        MiraiGate().StartPage(
                            "Main",
                            modifier = Modifier.padding(innerPadding),
                            onAfterAuth = { credentialres -> onSignInGoogle(credentialres) }
                        )
                    }
                }
            }
        } else {
            //トップ画面を表示する
            setContent {
                ITmiraiAndroidAppTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        MiraiGate().StartPage(
                            "Top",
                            modifier = Modifier.padding(innerPadding),
                            onAfterAuth = { credentialres -> onSignInGoogle(credentialres) }
                        )
                    }
                }
            }
        }
    }

    //Googleアカウントでログイン
    fun onSignInGoogle(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {
            // GoogleIdToken credential
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract the ID to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        signInGoogle(googleIdTokenCredential)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e(TAG, "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }


    fun signInGoogle(account: GoogleIdTokenCredential) {
        Log.i(TAG, "GoogleSignInAccount: ${account.id}, ${account.idToken}")
        val idToken = account.idToken
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnSuccessListener(){ task ->
                println("Successsssssssss")
                setContent {
                    ITmiraiAndroidAppTheme {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            MiraiGate().StartPage(
                                "Top",
                                modifier = Modifier.padding(innerPadding),
                                loginSuccess = true
                            )
                        }
                    }
                }
            }
            ?.addOnCompleteListener(this) { task ->
                println("Completeeeeees")
                if (task.isSuccessful) {
                    println("Completeeeeees")
                    if (account.id.contains("it-mirai-h.ibk.ed.jp")) {
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth?.currentUser
                        Log.i(TAG, "User: ${user?.uid}")
                        // Display main screen
                        user?.getIdToken(true)
                            ?.addOnCompleteListener { task2 ->
                                setContent {
                                    ITmiraiAndroidAppTheme {
                                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                            MiraiGate().StartPage(
                                                "Main",
                                                modifier = Modifier.padding(innerPadding),
                                                myProfile = API.get(APIPath.signIn, hashMapOf(), task2.result.token)[0]
                                            )
                                        }
                                    }
                                }
                            }

                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    fun signOut() {
        auth?.signOut()
        setContent {
            ITmiraiAndroidAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MiraiGate().StartPage(
                        "Top",
                        modifier = Modifier.padding(innerPadding),
                        onAfterAuth = { credentialres -> onSignInGoogle(credentialres) }
                    )
                }
            }
        }
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

