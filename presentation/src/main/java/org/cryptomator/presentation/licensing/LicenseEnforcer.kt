package org.cryptomator.presentation.licensing

import android.app.Activity
import androidx.annotation.StringRes
import org.cryptomator.presentation.R
import org.cryptomator.presentation.model.VaultModel
import javax.inject.Inject

/**
 * The license key system has been removed. The app is now fully unlocked for all users, so every
 * write-access check returns [true] and no license key or trial is required anymore.
 */
class LicenseEnforcer @Inject constructor() {

    enum class LockedAction(
        @StringRes val headerMessageRes: Int
    ) {
        CREATE_VAULT(R.string.screen_license_check_locked_create_vault),
        UPLOAD_FILES(R.string.screen_license_check_locked_upload_files),
        CREATE_FOLDER(R.string.screen_license_check_locked_create_folder),
        CREATE_TEXT_FILE(R.string.screen_license_check_locked_create_text_file),
        SHARE_NODE(R.string.screen_license_check_locked_share_node),
        RENAME_NODE(R.string.screen_license_check_locked_rename_node),
        MOVE_NODE(R.string.screen_license_check_locked_move_node),
        DELETE_NODE(R.string.screen_license_check_locked_delete_node);

        companion object {
            fun fromName(name: String?): LockedAction? {
                return entries.firstOrNull { it.name == name }
            }
        }
    }

    fun hasWriteAccess(): Boolean = true

    fun hasPaidLicense() = true

    fun startTrial() {}

    fun hasActiveTrial(): Boolean = true

    fun evaluateTrialState(): TrialState = TrialState(active = true, expired = false, formattedExpirationDate = null)

    data class TrialState(val isActive: Boolean, val isExpired: Boolean, val formattedExpirationDate: String?)

    data class LicenseUiState(
        val hasWriteAccess: Boolean,
        val hasPaidLicense: Boolean,
        val hasLifetimeLicense: Boolean,
        val hasRunningSubscription: Boolean,
        val trialState: TrialState
    )

    fun evaluateUiState(): LicenseUiState = LicenseUiState(
        hasWriteAccess = true,
        hasPaidLicense = true,
        hasLifetimeLicense = true,
        hasRunningSubscription = false,
        trialState = evaluateTrialState()
    )

    fun ensureWriteAccess(activity: Activity, action: LockedAction): Boolean = true

    fun hasWriteAccessForVault(vault: VaultModel?): Boolean = true

    fun peekWriteAccessForVault(vault: VaultModel?): Boolean = true

    fun ensureWriteAccessForVault(activity: Activity, vault: VaultModel?, action: LockedAction): Boolean = true
}
