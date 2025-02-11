package net.artificialwusslab.it_mirai_androidapp

import android.util.Log
import androidx.activity.ComponentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.JsonParser
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

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

    suspend fun ServerSignin(idToken: String? = ""): List<String>{
        val svTokenReq = API().getAPI(APIPath.signIn, hashMapOf(), idToken)[0]
        val svToken = JsonParser.parseString(svTokenReq).asJsonObject["token"].asString
        Log.d(TAG, svToken)
        val myProfile = API().getAPI(APIPath.myProfile, hashMapOf(), svToken)
        return myProfile
    }
}

data class AccountProfile(
    val uid: String,
    val name: String,
    val email: String,
    val photoURL: String,
    val gradeInSchool: Int,
    val classInSchool: String,
    val number: Int,
    val schoolClub: String,
    val permission: Int
)