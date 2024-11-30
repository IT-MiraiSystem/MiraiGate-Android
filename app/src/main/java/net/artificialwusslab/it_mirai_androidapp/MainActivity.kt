package net.artificialwusslab.it_mirai_androidapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.gson.JsonParser
import net.artificialwusslab.it_mirai_androidapp.Pages.NewUser
import net.artificialwusslab.it_mirai_androidapp.Pages.TopPage
import com.google.gson.Gson
import net.artificialwusslab.it_mirai_androidapp.ui.theme.ITmiraiAndroidAppTheme

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    private var auth: FirebaseAuth? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    private lateinit var TAG: String
    private var Access_Token: String? = null
    private var tokenResponse: String? = null
    private var accessToken: String? = null
    public var User: HashMap<String, String?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        window.statusBarColor = Color.Black.toArgb()
        TAG=resources.getString(R.string.app_name)
        Access_Token= JsonParser.parseString(API.post("AuthDevice", hashMapOf("DeviceName" to resources.getString(R.string.DeviceName),"Pass" to resources.getString(R.string.Pass)),null)[0]).asJsonObject.get("token").asString
        Log.i(TAG, "Access_Token: $Access_Token")
        if (auth?.currentUser != null) {
            //メイン画面を表示する
            val SearchUser=API.get("SerchUser", hashMapOf("uid" to auth?.currentUser?.uid.toString()),Access_Token)
            if(SearchUser[1]=="200") {
                setContent {
                    ITmiraiAndroidAppTheme {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Greeting(
                                name = "メイン画面",
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }else{
                signOut()
                setContent {
                    ITmiraiAndroidAppTheme {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            TopPage().UI(
                                modifier = Modifier.padding(innerPadding), onLoginClick = { signIn() })
                        }
                    }
                }
            }
        } else {
            //トップ画面を表示する
            setContent {
                ITmiraiAndroidAppTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        TopPage().UI(
                            modifier = Modifier.padding(innerPadding), onLoginClick = { signIn() }
                        )
                    }
                }
            }
        }
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    Log.i(TAG, "GoogleSignInAccount: ${account.email}")
                    val idToken = account.idToken
                    if (idToken != null) {
                        val credential = GoogleAuthProvider.getCredential(idToken, null)
                        auth?.signInWithCredential(credential)
                            ?.addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    if (account.email?.contains("it-mirai-h.ibk.ed.jp") == true) {
                                        Log.d(TAG, "signInWithCredential:success")
                                        val user = auth?.currentUser
                                        Log.i(TAG, "User: ${user?.uid}")
                                        val user_search = API.get("SerchUser", hashMapOf("uid" to user?.uid.toString()), Access_Token)
                                        Log.i(TAG, "UserSearch: ${user_search[0]}" + "ResponseCode: ${user_search[1]}")
                                        if (user_search[1] == "200") {
                                            // Display main screen
                                            setContent {
                                                ITmiraiAndroidAppTheme {
                                                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                                        Greeting(
                                                            name = "アカウント登録完了画面（すでに存在していた場合）",
                                                            modifier = Modifier.padding(innerPadding)
                                                        )
                                                    }
                                                }
                                            }
                                        } else {
                                            val UserInfo = hashMapOf(
                                                "uid" to user?.uid,
                                                "name" to user?.displayName,
                                                "email" to user?.email,
                                                "photoUrl" to user?.photoUrl.toString()
                                            )
                                            // Display new user registration screen
                                            setContent {
                                                ITmiraiAndroidAppTheme {
                                                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                                        NewUser().UI(
                                                            onDismissRequest = {
                                                                signOut()
                                                            },
                                                            onConfirmation = {
                                                                //空欄の選択肢があればToastでエラーを出す
                                                                if (UserInfo["GradeInSchool"] != null && UserInfo["ClassInSchool"] != null && UserInfo["SchoolClub"] != null) {
                                                                    Log.i(TAG, "UserInfo: $UserInfo")
                                                                    var response=API.post("NewUser",UserInfo,Access_Token);
                                                                    Log.i(TAG,"response:"+response[0]+"ResponseCode:"+response[1])
                                                                    if (response[1]=="200"){
                                                                        User =UserInfo
                                                                        setContent{
                                                                            ITmiraiAndroidAppTheme {
                                                                                Scaffold(modifier = Modifier.fillMaxSize()) {
                                                                                    Greeting(
                                                                                        name = "アカウント登録完了画面",
                                                                                        modifier = Modifier.padding(
                                                                                            innerPadding
                                                                                        )
                                                                                    )
                                                                                }
                                                                            }
                                                                        }
                                                                    }else{
                                                                        Toast.makeText(this, "登録に失敗しました", Toast.LENGTH_SHORT).show()
                                                                    }
                                                                }else{
                                                                    Toast.makeText(this, "選択肢を入力してください", Toast.LENGTH_SHORT).show()
                                                                }
                                                            },
                                                            dialogTitle = "新規ユーザー登録",
                                                            dialogText = "学年、クラス、部活を選択してください",
                                                            icon = Icons.Default.Info,
                                                            GradeInSchool = arrayOf("1年", "2年", "3年", "4年"),
                                                            GradeInSchoolOptionSelected = { option ->
                                                                UserInfo.apply {
                                                                    put("GradeInSchool", option)
                                                                }
                                                            },
                                                            ClassInSchool = arrayOf("F", "M"),
                                                            ClassInSchoolOptionSelected = { option ->
                                                                UserInfo.apply {
                                                                    put("ClassInSchool", option)
                                                                }
                                                            },
                                                            SchoolClub = arrayOf(
                                                                "情報システム部",
                                                                "情報デザイン部",
                                                                "eスポーツ部"
                                                            ),
                                                            SchoolClubOptionSelected = { option ->
                                                                UserInfo.apply {
                                                                    put("SchoolClub", option)
                                                                }
                                                            }
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
                    } else {
                        Log.w(TAG, "Google sign in failed: idToken is null")
                    }
                } else {
                    Log.w(TAG, "Google sign in failed: account is null")
                }
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    //Googleアカウントでログイン
    private fun signIn() {
        Log.d(TAG, "signIn")
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun signOut() {
        auth?.signOut()
        setContent {
            ITmiraiAndroidAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TopPage().UI(
                        modifier = Modifier.padding(innerPadding), onLoginClick = { signIn() }
                    )
                }
            }
        }
    }

}

private operator fun Unit.invoke(modifier: Modifier, onLoginClick: () -> Unit) {

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

