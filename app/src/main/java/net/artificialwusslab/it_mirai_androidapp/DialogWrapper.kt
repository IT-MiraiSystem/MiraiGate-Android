package net.artificialwusslab.it_mirai_androidapp

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class DialogWrapper {

    @Composable
    fun Error(
        text: String = "エラーが発生しました",
        onConfirmation: (() -> Unit)? = null,
        onDismissRequest: (() -> Unit)? = null
    ){
        var show by remember { mutableStateOf(true) }
        if(show) {
            AlertDialog(
                icon = {
                    Icon(Icons.Default.Warning, contentDescription = "Example Icon")
                },
                title = {
                    Text(text = "エラー")
                },
                text = {
                    Text(text = text)
                },
                onDismissRequest = {
                    show = false
                },
                confirmButton = {
                    if(onConfirmation != null) {
                        TextButton(
                            onClick = {
                                onConfirmation()
                                show = false
                            }
                        ) {
                            Text("OK")
                        }
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            show = false
                        }
                    ) {
                        Text("閉じる")
                    }
                }
            )
        }
    }
}