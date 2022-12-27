package com.example.attractions

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.getDateString(): String {
    return SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(this.timeInMillis)
}

fun Fragment.isPermissionsGranted(
    launcher: ActivityResultLauncher<Array<String>>,
    permissionList: Array<String>,
): Boolean {
    val isAllGranted = permissionList.all { permission ->
        ContextCompat.checkSelfPermission(
            this.requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
    return if (isAllGranted) {
        true
    } else {
        launcher.launch(permissionList)
        false
    }
}

