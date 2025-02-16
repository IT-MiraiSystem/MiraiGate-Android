package net.artificialwusslab.it_mirai_androidapp

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.credentials.GetCredentialResponse
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import net.artificialwusslab.it_mirai_androidapp.ui.MainPage
import net.artificialwusslab.it_mirai_androidapp.ui.SettingsItem
import net.artificialwusslab.it_mirai_androidapp.ui.SettingsPage
import net.artificialwusslab.it_mirai_androidapp.ui.TopPage

class MiraiGate {
    val TAG: String = "MiraiGate"
    @Composable
    fun StartPage(pageName: String, modifier: Modifier, onAfterAuth: (GetCredentialResponse) -> Unit = {}, loginSuccess: Int = 0, myProfile: List<String> = listOf()){
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = pageName) {
            composable("Top") {
                TopPage().UI(
                    modifier = modifier,
                    onAfterAuth = onAfterAuth,
                    loginSuccess = loginSuccess
                )
            }
            composable("Main") {
                //navController.popBackStack()
                MainPage().UI(
                    modifier = modifier,
                    myProfile  = myProfile,
                    miraiGateNavController = navController
                )
            }
            composable("Settings"){
                SettingsPage().UI(miraiGateNavController = navController)
            }
            composable("Settings/Features/{SettingItem}", arguments = listOf(
                navArgument("SettingItem"){
                    type = NavType.StringType
                    nullable = false
                    defaultValue = "Unkonow"
                }
            )){ nav ->
                println(nav.arguments?.getString("SettingItem"))
                SettingsPage().UI(miraiGateNavController = navController)
            }
            composable("Settings/App/{SettingItem}", arguments = listOf(
                navArgument("SettingItem"){
                    type = NavType.StringType
                    nullable = false
                    defaultValue = "Unkonow"
                }
            )){ nav ->
                println(nav.arguments?.getString("SettingItem"))
                SettingsPage().UI(miraiGateNavController = navController)
            }
            composable("Settings/About/{SettingItem}", arguments = listOf(
                navArgument("SettingItem"){
                    type = NavType.StringType
                    nullable = false
                    defaultValue = "Unkonow"
                }
            )){ nav ->
                var settingItem = nav.arguments?.getString("SettingItem")
                if(settingItem == "OpenLicenses"){
                    SettingsItem().OpenLicenses(miraiGateNavController = navController)
                }

            }
        }
//        if(pageName == "Top"){
//            navController.navigate("Top")
//        }
//        else if(pageName == "Main"){
//            navController.navigate("Main")
//        }
        Log.i(TAG, myProfile.toString())
    }
}