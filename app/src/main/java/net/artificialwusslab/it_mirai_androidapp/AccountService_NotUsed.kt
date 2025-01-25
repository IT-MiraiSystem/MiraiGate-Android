package net.artificialwusslab.it_mirai_androidapp

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import net.artificialwusslab.it_mirai_androidapp.ui.NewUser
import net.artificialwusslab.it_mirai_androidapp.ui.TopPage
import net.artificialwusslab.it_mirai_androidapp.ui.theme.ITmiraiAndroidAppTheme

class AccountService_NotUsed: ComponentActivity(){
    val TAG = "ITMirai_AccountService"
    var User: HashMap<String, String?>? = null
    val auth = Firebase.auth
    var Access_Token: String? = null

    ///@Composable
    fun onSignInGoogle(result: GetCredentialResponse) {
//        val navController = rememberNavController()
//        NavHost(navController, startDestination = "m3"){
//            composable("acRegistComplete1") {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "アカウント登録完了画面",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//            composable("acRegistComplete2") {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "アカウント登録完了画面（すでに存在していた場合）",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//            composable("newUserRegist") { us ->
//                NewUserRegister()
//            }
//            //activity("vib") { activityClass = VibPage::class }
//        }
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

    fun signInGoogle(account: GoogleIdTokenCredential){
        Log.i(TAG, "GoogleSignInAccount: ${account.id}, ${account.idToken}")
        val idToken = account.idToken
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        if (account.id.contains("it-mirai-h.ibk.ed.jp")) {
                            Log.d(TAG, "signInWithCredential:success")
                            val user = auth.currentUser
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
                                        NewUserRegister()
                                    }
                                }
                            }
                        }
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                    }
                }
    }


    fun signInGoogle_dbg(account: GoogleIdTokenCredential){
        Log.i(TAG, "GoogleSignInAccount: ${account.id}, ${account.idToken}")
        val idToken = account.idToken
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (account.id.contains("it-mirai-h.ibk.ed.jp")) {
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth.currentUser
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
                                    NewUserRegister()
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
        auth.signOut()
        setContent {
            ITmiraiAndroidAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TopPage().UI(
                        modifier = Modifier.padding(innerPadding), onLoginClick = {  },
                        onAfterAuth = {}
                    )
                }
            }
        }
    }

    // Display new user registration screen
    @Composable
    fun NewUserRegister(){
        var user = auth.currentUser
        val UserInfo = hashMapOf(
            "uid" to user?.uid,
            "name" to user?.displayName,
            "email" to user?.email,
            "photoUrl" to user?.photoUrl.toString()
        )
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
}