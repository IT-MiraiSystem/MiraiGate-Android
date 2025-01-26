package net.artificialwusslab.it_mirai_androidapp

import androidx.activity.ComponentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountService{
    val TAG = "MiraiGate_AccountService"
    var User: HashMap<String, String?>? = null
    val auth = Firebase.auth

    fun GetFirebaseToken(){
        //var token: String? = "TOKEN"
        auth.currentUser?.getIdToken(true)
            ?.addOnCompleteListener { task ->
                task.result.token
            }
        //return token
    }
}