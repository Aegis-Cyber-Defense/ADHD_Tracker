package com.aegis.adhdtracker.ui.health

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord

@Composable
fun HealthPermissionButton(
    onPermissionsGranted: () -> Unit
) {
    val context = LocalContext.current
    val sdkStatus = HealthConnectClient.getSdkStatus(context)

    val permissions = setOf(
        HealthPermission.getReadPermission(HeartRateRecord::class),
        HealthPermission.getReadPermission(SleepSessionRecord::class)
    )

    val launcher = rememberLauncherForActivityResult(
        contract = PermissionController.createRequestPermissionResultContract()
    ) { grantedPermissions ->
        if (grantedPermissions.containsAll(permissions)) {
            onPermissionsGranted()
        }
    }

    Button(
        onClick = {
            if (sdkStatus == HealthConnectClient.SDK_AVAILABLE) {
                launcher.launch(permissions)
            }
        },
        enabled = sdkStatus == HealthConnectClient.SDK_AVAILABLE
    ) {
        Text(
            text = if (sdkStatus == HealthConnectClient.SDK_AVAILABLE) {
                "Grant Health Connect Access"
            } else {
                "Health Connect Unavailable"
            }
        )
    }
}
