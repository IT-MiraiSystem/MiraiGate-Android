package net.artificialwusslab.it_mirai_androidapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import net.artificialwusslab.it_mirai_androidapp.Pages.NewUser
import net.artificialwusslab.it_mirai_androidapp.Pages.TopPage
import net.artificialwusslab.it_mirai_androidapp.ui.theme.ITmiraiAndroidAppTheme

@Suppress("DEPRECATION")
class MainActivity_2 : ComponentActivity() {
    private var auth: FirebaseAuth? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    private var TAG: String = "ITmirai_Main"
    //private var Access_Token: String? = null
    private var tokenResponse: String? = null
    private var accessToken: String? = null
    public var User: HashMap<String, String?>? = null
    private val patter2launcher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult ->
            pattern2SignInGoogleResult(result)
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.i("fhaowe", "めいんあくてぃびりいつー！！！")
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(resources.getString(R.string.default_web_client_id))
            .setAutoSelectEnabled(true)
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val credentialRequest: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        //window.statusBarColor = Color.Black.toArgb()
        //TAG=resources.getString(R.string.app_name)
        //val AuthDeviceReq = API.post("AuthDevice", hashMapOf("DeviceName" to resources.getString(R.string.DeviceName),"Pass" to resources.getString(R.string.Pass)),null)
        //if (AuthDeviceReq[2] == "false") {

        if(false) {
            setContent {
                ITmiraiAndroidAppTheme {
                    Scaffold(modifier = Modifier.padding(10.dp)) { innerPadding ->
                        val uriHandler = LocalUriHandler.current
                        Text(buildAnnotatedString {
                            append("デバッグ用サンドボックスDeath")
                            withLink(
                                LinkAnnotation.Url(
                                    "https://classroom.google.com/",
                                )
                            ) {
                                append("クラスルームを開け")
                            }
                        },
                            modifier =  Modifier.padding(innerPadding))
                    }
                    DialogWrapper().Error(
                        text = "サーバーエラーが発生しました。\nしばらくしてからもう一度試してください。\n\n詳細:\nAuthDevice\naaaa",
                    )
                }
            }
            return
        }
            //return
        //}
        //Access_Token= JsonParser.parseString(AuthDeviceReq[0]).asJsonObject.get("token").asString
        //Log.i(TAG, "Access_Token: $Access_Token")
        Log.d(TAG, auth?.currentUser.toString())
        if (auth?.currentUser == null) {
            //メイン画面を表示する
            //val SearchUser=API.get("SerchUser", hashMapOf("uid" to auth?.currentUser?.uid.toString()),Access_Token)
                signOut()
            //トップ画面を表示する
            setContent {
                ITmiraiAndroidAppTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        TopPage().UI(
                            modifier = Modifier.padding(innerPadding), onLoginClick = { credentialres ->
                                onSignInGoogle(credentialres)
                                //setContent {
                                    //ITmiraiAndroidAppTheme {
                                        //signInTapped(googleIdOption, credentialRequest)
                                    //}
                                //}
                            },
                            onAfterAuth = {}
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
                Log.d(TAG, account.email.toString())
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
                                        //val user_search = API.get("SerchUser", hashMapOf("uid" to user?.uid.toString()), Access_Token)
                                        //Log.i(TAG, "UserSearch: ${user_search[0]}" + "ResponseCode: ${user_search[1]}")
                                        if (false) {
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
                                            setContent {
                                                ITmiraiAndroidAppTheme {
                                                    NewUserRegister(UserInfo)
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

    // Display new user registration screen
    @Composable
    fun NewUserRegister(UserInfo: HashMap<String, String?>){
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NewUser().UI(
                onDismissRequest = {
                    signOut()
                },
                onConfirmation = {
                    //空欄の選択肢があればToastでエラーを出す
                    if (UserInfo["GradeInSchool"] != null && UserInfo["ClassInSchool"] != null && UserInfo["SchoolClub"] != null) {
                        Log.i(TAG, "UserInfo: $UserInfo")
                        //var response=API.post("NewUser",UserInfo,Access_Token);
                        //Log.i(TAG,"response:"+response[0]+"ResponseCode:"+response[1])
                        if ("200"=="200"){
                            User =UserInfo
                            setContent{
                                ITmiraiAndroidAppTheme {
                                        Greeting(
                                            name = "アカウント登録完了画面",
                                            modifier = Modifier.padding(
                                                innerPadding
                                            )
                                        )
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

    private fun pattern2SignInGoogleResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            if (result.data != null) {
                val credential = try {
                    Identity.getSignInClient(this).getSignInCredentialFromIntent(result.data)
                } catch (e: Exception) {
                    // ログインキャンセル時等にExceptionが発生する
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    null
                }
                val idToken = credential?.googleIdToken
                if (idToken != null) {
                    Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
                }
                Toast.makeText(this, credential?.id, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "result code is not RESULT_OK", Toast.LENGTH_SHORT).show()
        }
    }

    //Googleアカウントでログイン
    private fun signIn() {
        Log.d(TAG, "signIn")
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Composable
    fun signInTapped(googleIdOption: GetGoogleIdOption, credentialRequest: GetCredentialRequest){
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val credentialManager = CredentialManager.create(context)
        LaunchedEffect(key1 = null)  {
            try {
                val result = credentialManager.getCredential(
                    request = credentialRequest,
                    context = context,
                )
                onSignInGoogle(result)
            } catch (e: GetCredentialException) {
                Log.e(TAG, "Credential Error.")
            }
        }
    }

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
                        signInGoogle_dbg(googleIdTokenCredential)
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

    /*
    fun signInGoogle(account: GoogleIdTokenCredential){
        Log.i(TAG, "GoogleSignInAccount: ${account.id}, ${account.idToken}")
        val idToken = account.idToken
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (account.id.contains("it-mirai-h.ibk.ed.jp")) {
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
                            setContent {
                                ITmiraiAndroidAppTheme {
                                    NewUserRegister(UserInfo)
                                }
                            }
                        }
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

     */

    fun signInGoogle_dbg(account: GoogleIdTokenCredential){
        Log.i(TAG, "GoogleSignInAccount: ${account.id}, ${account.idToken}")
        val idToken = account.idToken
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Log.i(TAG, "fhafew")
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                Log.i(TAG, task.isSuccessful.toString())
                if (task.isSuccessful) {
                    if (account.id.contains("it-mirai-h.ibk.ed.jp")) {
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth?.currentUser
                        Log.i(TAG, "User: ${user?.uid}")
                        //val user_search = API.get("SerchUser", hashMapOf("uid" to user?.uid.toString()), Access_Token)
                        //Log.i(TAG, "UserSearch: ${user_search[0]}" + "ResponseCode: ${user_search[1]}")
                        if (false) {
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
                            setContent {
                                ITmiraiAndroidAppTheme {
                                    NewUserRegister(UserInfo)
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
                    TopPage().UI(
                        modifier = Modifier.padding(innerPadding), onLoginClick = { signIn() },
                        onAfterAuth = {}
                    )
                }
            }
        }
    }

}

private operator fun Unit.invoke(modifier: Modifier, onLoginClick: () -> Unit) {

}

@Composable
fun Greeting_2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview_2() {
    ITmiraiAndroidAppTheme {
        Greeting("Android")
    }
}

