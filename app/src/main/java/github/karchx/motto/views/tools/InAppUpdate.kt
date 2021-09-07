package github.karchx.motto.views.tools

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import github.karchx.motto.R

class InAppUpdate(activity: Activity) : InstallStateUpdatedListener {

    private var appUpdateManager: AppUpdateManager
    private val REQUEST_CODE = 500
    private var parentActivity: Activity = activity

    private var currentType = AppUpdateType.FLEXIBLE

    init {

        appUpdateManager = AppUpdateManagerFactory.create(parentActivity)
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            // Check if update is available
            if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) { // UPDATE IS AVAILABLE
                try {
                    startUpdate(info, AppUpdateType.IMMEDIATE)
                } catch (ex: Exception) {

                }

            } else {
                // UPDATE IS NOT AVAILABLE
            }
        }
        appUpdateManager.registerListener(this)
    }


    private fun startUpdate(info: AppUpdateInfo, type: Int) {
        appUpdateManager.startUpdateFlowForResult(info, type, parentActivity, REQUEST_CODE)
        currentType = type
    }

    fun onResume() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (currentType == AppUpdateType.FLEXIBLE) {
                // If the update is downloaded but not installed, notify the user to complete the update.
                if (info.installStatus() == InstallStatus.DOWNLOADED)
                    flexibleUpdateDownloadCompleted()
            } else if (currentType == AppUpdateType.IMMEDIATE) {
                // for AppUpdateType.IMMEDIATE only, already executing updater
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    try {
                        startUpdate(info, AppUpdateType.IMMEDIATE)
                    } catch (ex: Exception) {

                    }
                }
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode != AppCompatActivity.RESULT_OK) {
                // If the update is cancelled or fails, you can request to start the update again.
                Log.e("ERROR", "Update flow failed! Result code: $resultCode")
            }
        }
    }

    private fun flexibleUpdateDownloadCompleted() {
        Snackbar.make(
            parentActivity.findViewById(R.id.nav_host_fragment_activity_main),
            "Обновление установлено.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("Перезапуск") { appUpdateManager.completeUpdate() }
            setActionTextColor(Color.WHITE)
            show()
        }
    }

    fun onDestroy() {
        appUpdateManager.unregisterListener(this)
    }

    override fun onStateUpdate(state: InstallState) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            flexibleUpdateDownloadCompleted()
        }
    }

}