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
import io.ktor.util.InternalAPI
import kotlinx.coroutines.runBlocking
import java.net.URL

class API {
    private val client = HttpClient()
    private var url: URL = URL("https://artificialwusslab.net/api/ITmiraiApp/")
    private val TAG = "ITmiraiAPI"
    suspend fun getAPI(pass: String, param: Map<String, String>,AccessToken:String): List<String> {
        val requestParam = param.entries.joinToString("&") { "${it.key}=${it.value}" }
        Log.i(TAG, "Request: $url$pass?$requestParam")
        val response = client.get("$url$pass?$requestParam") {
            header("Authorization", "Bearer $AccessToken")
        }
        return "${response.bodyAsText()}^*${response.status.value}".split("^*")
    }

    @OptIn(InternalAPI::class)
    suspend fun postAPI(pass: String, param: HashMap<String, String?>,AccessToken:String?): List<String> {
        val requestJson = Gson().toJson(param)
        if (AccessToken==null){
            val response: HttpResponse = client.post("$url$pass") {
                body = requestJson
                contentType(ContentType.Application.Json)
            }
            return "${response.bodyAsText()}^*${response.status.value}".split("^*")
        }else {
            val response: HttpResponse = client.post("$url$pass") {
                body = requestJson
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $AccessToken")
            }
            return "${response.bodyAsText()}^*${response.status.value}".split("^*")
        }
    }

    companion object {
        fun get(pass: String, param: Map<String, String>,AccessToken: String?): List<String> = runBlocking {
            if (AccessToken!=null){
                API().getAPI(pass, param,AccessToken)
            }else{
                API().getAPI(pass, param, null.toString())
            }
        }
        fun post(pass: String, param: HashMap<String, String?>,AccessToken: String?): List<String> = runBlocking {
            if(AccessToken!=null) {
                API().postAPI(pass, param, AccessToken)
            }else{
                API().postAPI(pass, param, null.toString())
            }
        }
    }
}