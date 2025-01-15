package com.example.bottomnavigationandbottomsheet.activities

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomText
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel
import com.example.data.network.ConnectionState
import com.example.data.network.utils.observeConnectivityAsFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest


open class BaseActivity : ComponentActivity() {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Composable
    fun connection_state(): ConnectionState {
        var connectionState by remember { mutableStateOf(ConnectionState.Unavailable) }
        val context = LocalContext.current.applicationContext
        LaunchedEffect(context) {
            context.observeConnectivityAsFlow().collectLatest {
                connectionState = it
            }
        }
        return connectionState
    }
    @Composable
    fun manageBackPress(sharedViewModel: SharedViewModel)
    {
        val confirmExitDialogState by sharedViewModel.confirmExitDialogState.collectAsState()

        BackHandler {
            sharedViewModel.showConfirmExitDialog()
        }
        if (confirmExitDialogState) {
            ConfirmExitDialog(
                onConfirm = {
                    sharedViewModel.dismissConfirmExitDialog()
                    finishAffinity()
                },
                onDismiss = {
                    sharedViewModel.dismissConfirmExitDialog()
                }
            )
        }
    }

    @Composable
    fun getIconTintColor(selected: Boolean): Color {
        val colors = MaterialTheme.colorScheme
        return if (selected) {
            colors.primary
        } else {
            colors.secondary
        }
    }

    @Composable
    fun ConfirmExitDialog(
        onConfirm: () -> Unit,
        onDismiss: () -> Unit
    ) {
        AlertDialog(
           // containerColor = colorResource(id = R.color.bg_color),
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    CustomText(text = "Exit")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    CustomText(text ="Cancel")
                }
            },
            title = {
                CustomText(text = "Confirm Exit",
                    fontSize = 20,
                    fontWeight = FontWeight.Bold)
            },
            text = {
                CustomText(text ="Are you sure you want to exit the application?")
            }
        )
    }
}