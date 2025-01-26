package net.artificialwusslab.it_mirai_androidapp

import android.util.Log
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.util.InternalAPI
import kotlinx.coroutines.runBlocking
import java.net.URL

class API {
    private val client = HttpClient()
    private var url: URL = URL("https://core.yarukihaoutide.online/api/")
    val TAG = "MiraiGate_API"
    suspend fun getAPI(pass: String, param: Map<String, String>,AccessToken:String?): List<String> {
        val requestParam = param.entries.joinToString("&") { "${it.key}=${it.value}" }
        Log.i(TAG, "Request: $url$pass?$requestParam")
        if (AccessToken==null) {
            val response = client.get("$url$pass?$requestParam") {
            }
            return "${response.bodyAsText()}^*${response.status.value}^*${response.status.description}^*${response.status.isSuccess()}".split("^*")
        }else{
            val response = client.get("$url$pass?$requestParam") {
                header("Authorization", "Bearer $AccessToken")
            }
            return "${response.bodyAsText()}^*${response.status.value}^*${response.status.description}^*${response.status.isSuccess()}".split("^*")
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun postAPI(pass: String, param: HashMap<String, String?>,AccessToken:String?): List<String> {
        val requestJson = Gson().toJson(param)
        Log.i(TAG, "Request: $url$pass")
        Log.i(TAG, "RequestJson: $requestJson")
        if (AccessToken==null){
            val response: HttpResponse = client.post("$url$pass") {
                body = requestJson
                contentType(ContentType.Application.Json)
            }
            return "${response.bodyAsText()}^*${response.status.value}^*${response.status.description}^*${response.status.isSuccess()}".split("^*")
        }else {
            val response: HttpResponse = client.post("$url$pass") {
                body = requestJson
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $AccessToken")
            }
            return "${response.bodyAsText()}^*${response.status.value}^*${response.status.description}^*${response.status.isSuccess()}".split("^*")
        }
    }

    companion object {
        fun get(pass: String, param: Map<String, String>,AccessToken: String?): List<String> = runBlocking {
            if (AccessToken!=null){
                API().getAPI(pass, param,AccessToken)
            }else{
                API().getAPI(pass, param, null)
            }
        }
        fun post(pass: String, param: HashMap<String, String?>,AccessToken: String?): List<String> = runBlocking {
            if(AccessToken!=null) {
                API().postAPI(pass, param, AccessToken)
            }else{
                API().postAPI(pass, param, null)
            }
        }
    }

    data class JWTResponseType(
        val status: String,
        val token: String?,
        val message: String?
    )
}

object APIPath {
    const val ping: String = "ping"
    const val signIn: String = "signin"
    const val goSchool: String = "GoSchool"
    const val lessonDetails: String = "LessonDetails"
    const val userList: String = "userList"
    const val getIssues: String = "getissues"
    const val myProfile: String = "myprofile"
    const val events: String = "events"
    const val lessonChange: String = "LessonChange"
    const val insertSubject: String = "InsertSubject"
    const val issueRegister:String = "issuesRegister"
    const val getattendance: String = "getattendance"
    const val insertEvent: String = "InsertEvent"
}