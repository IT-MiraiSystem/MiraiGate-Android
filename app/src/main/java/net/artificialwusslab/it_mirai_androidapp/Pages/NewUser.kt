package net.artificialwusslab.it_mirai_androidapp.Pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

class NewUser {
    @Composable
    fun UI(
        onDismissRequest: () -> Unit,
        onConfirmation: () -> Unit,
        dialogTitle: String,
        dialogText: String,
        icon: ImageVector,
        GradeInSchool: Array<String>,
        GradeInSchoolOptionSelected: (String) -> Unit,
        ClassInSchool: Array<String>,
        ClassInSchoolOptionSelected: (String) -> Unit,
        SchoolClub: Array<String>,
        SchoolClubOptionSelected: (String) -> Unit
    ) {
        var gradeExpanded by remember { mutableStateOf(false) }
        var selectedGrade by remember { mutableStateOf<String?>(null) }
        var classExpanded by remember { mutableStateOf(false) }
        var selectedClass by remember { mutableStateOf<String?>(null) }
        var clubExpanded by remember { mutableStateOf(false) }
        var selectedClub by remember { mutableStateOf<String?>(null) }
        
        AlertDialog(
            icon = {
                Icon(icon, contentDescription = "Example Icon")
            },
            title = {
                Text(text = dialogTitle)
            },
            text = {
                Column {
                    Text(text = dialogText, modifier = Modifier.padding(bottom = 10.dp))

                    // 学年の選択
                    TextButton(onClick = { gradeExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                        Text(selectedGrade ?: "学年")
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                        DropdownMenu(
                            expanded = gradeExpanded,
                            onDismissRequest = { gradeExpanded = false }
                        ) {
                            GradeInSchool.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        selectedGrade = option
                                        GradeInSchoolOptionSelected(option)
                                        gradeExpanded = false
                                    }
                                )
                            }
                        }
                    }


                    // クラスの選択
                    TextButton(onClick = { classExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                        Text(selectedClass ?: "クラス")
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                        DropdownMenu(
                            expanded = classExpanded,
                            onDismissRequest = { classExpanded = false }
                        ) {
                            ClassInSchool.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        selectedClass = option
                                        ClassInSchoolOptionSelected(option)
                                        classExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // 部活の選択
                    TextButton(onClick = { clubExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                        Text(selectedClub ?: "部活")
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                        DropdownMenu(
                            expanded = clubExpanded,
                            onDismissRequest = { clubExpanded = false }
                        ) {
                            SchoolClub.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        selectedClub = option
                                        SchoolClubOptionSelected(option)
                                        clubExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            },
            onDismissRequest = { onDismissRequest() },
            confirmButton = {
                TextButton(onClick = { onConfirmation() }) {
                    Text("登録")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text("キャンセル")
                }
            }
        )
    }
}
