package com.example.engmas

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.engmas.ui.EngMasApp
import com.example.engmas.ui.theme.EngMasTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            EngMasTheme {
                EngMasApp()
            }
        }
    }
}

@Composable
fun RequestNotificationPermission() {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showNotification(context)
            Toast.makeText(context, "Permission Granted!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            when {
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> {
                    // Đã có quyền
                    showNotification(context)
                }

                ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission) -> {
                    // Người dùng từng từ chối nhưng chưa chọn "Don't ask again"
                    permissionLauncher.launch(permission)
                }

                else -> {
                    // Người dùng từ chối và tick "Don't ask again"
                    Toast.makeText(
                        context,
                        "Vui lòng cấp quyền trong phần Cài đặt nếu muốn nhận thông báo",
                        Toast.LENGTH_LONG
                    ).show()

                    // Optional: mở trang cài đặt của app
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }
            }
        }

    }
}

fun showNotification(context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "default_channel",
            "Default Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, "default_channel")
        .setContentTitle("Chào mừng!")
        .setContentText("Bạn đã bật thông báo thành công 🎉")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .build()

    notificationManager.notify(1, notification)
}
