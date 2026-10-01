/*
 * Copyright 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wuc656.nowinandroid.update

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "InAppUpdateHelper"

sealed interface InAppUpdateUiState {
    data object Idle : InAppUpdateUiState
    data object Downloading : InAppUpdateUiState
    data object Downloaded : InAppUpdateUiState
    data class Available(val appUpdateInfo: AppUpdateInfo) : InAppUpdateUiState
    data class Error(val message: String) : InAppUpdateUiState
}

@Singleton
class InAppUpdateHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val appUpdateManager: AppUpdateManager by lazy {
        AppUpdateManagerFactory.create(context)
    }

    private val _updateUiState = MutableStateFlow<InAppUpdateUiState>(InAppUpdateUiState.Idle)
    val updateUiState: StateFlow<InAppUpdateUiState> = _updateUiState.asStateFlow()

    private val installStateUpdatedListener = InstallStateUpdatedListener { state: InstallState ->
        when (state.installStatus()) {
            InstallStatus.DOWNLOADING -> {
                _updateUiState.value = InAppUpdateUiState.Downloading
            }
            InstallStatus.DOWNLOADED -> {
                _updateUiState.value = InAppUpdateUiState.Downloaded
            }
            InstallStatus.FAILED -> {
                _updateUiState.value = InAppUpdateUiState.Error("Update download failed with error code ${state.installErrorCode()}")
            }
            InstallStatus.INSTALLED -> {
                _updateUiState.value = InAppUpdateUiState.Idle
            }
            else -> {
                Log.d(TAG, "InstallStatus: ${state.installStatus()}")
            }
        }
    }

    init {
        try {
            appUpdateManager.registerListener(installStateUpdatedListener)
        } catch (e: Exception) {
            Log.w(TAG, "Failed to register In-App Update listener: ${e.message}")
        }
    }

    fun checkForUpdate(
        onUpdateAvailable: ((AppUpdateInfo) -> Unit)? = null,
    ) {
        try {
            appUpdateManager.appUpdateInfo
                .addOnSuccessListener { info ->
                    if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                        info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                    ) {
                        _updateUiState.value = InAppUpdateUiState.Available(info)
                        onUpdateAvailable?.invoke(info)
                    } else if (info.installStatus() == InstallStatus.DOWNLOADED) {
                        _updateUiState.value = InAppUpdateUiState.Downloaded
                    }
                }
                .addOnFailureListener { error ->
                    Log.d(TAG, "In-App update check failed or not available: ${error.message}")
                }
        } catch (e: Exception) {
            Log.w(TAG, "Unable to check for in-app updates: ${e.message}")
        }
    }

    fun startFlexibleUpdate(
        activity: Activity,
        launcher: ActivityResultLauncher<IntentSenderRequest>,
        appUpdateInfo: AppUpdateInfo,
    ) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                launcher,
                AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build(),
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start in-app update flow: ${e.message}")
        }
    }

    fun completeUpdate() {
        try {
            appUpdateManager.completeUpdate()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to complete update: ${e.message}")
        }
    }

    fun onDestroy() {
        try {
            appUpdateManager.unregisterListener(installStateUpdatedListener)
        } catch (e: Exception) {
            Log.w(TAG, "Failed to unregister In-App Update listener: ${e.message}")
        }
    }
}
