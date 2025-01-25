package com.darleyleal.financebuddy.presetation.screens.start

import android.annotation.SuppressLint
import android.app.Application
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.darleyleal.financebuddy.presetation.app.MainActivity
import com.darleyleal.financebuddy.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StarViewModel @Inject constructor(
    private val application: Application,
) : AndroidViewModel(application) {

    private val _authenticationSucceeded = MutableStateFlow("")
    val authenticationSucceeded = _authenticationSucceeded.asStateFlow()

    private val _authenticationError = MutableStateFlow("")
    val authenticationError = _authenticationError.asStateFlow()

    private val _authenticationFailed = MutableStateFlow("")
    val authenticationFailed = _authenticationFailed.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    @SuppressLint("SwitchIntDef")
    fun showBiometricPrompt(activity: MainActivity) {
        val executor = ContextCompat.getMainExecutor(activity)

        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    _authenticationError.value =
                        application.applicationContext.getString(
                            R.string.authentication_error,
                            errString
                        )
                    _isAuthenticated.value = false
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    _authenticationSucceeded.value =
                        application.applicationContext.getString(
                            R.string.authentication_succeeded
                        )
                    _isAuthenticated.value = true
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    _authenticationFailed.value =
                        application.applicationContext.getString(
                            R.string.authentication_failed
                        )
                    _isAuthenticated.value = false
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(
                application.applicationContext.getString(
                    R.string.unlock_your_cell_phone
                )
            )
            .setSubtitle(
                application.applicationContext.getString(
                    R.string.use_pattern
                )
            )
            .setAllowedAuthenticators(
                BIOMETRIC_STRONG or DEVICE_CREDENTIAL
            )
            .build()

        val biometricManager = BiometricManager.from(application.applicationContext)
        when (biometricManager.canAuthenticate(
            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        )) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                biometricPrompt.authenticate(promptInfo)
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                _authenticationError.value = application.applicationContext.getString(
                    R.string.authentication_error
                )
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                _authenticationFailed.value =
                    application.applicationContext.getString(
                        R.string.authentication_failed
                    )
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                _authenticationFailed.value =
                    application.applicationContext.getString(
                        R.string.authentication_failed
                    )
            }
        }
    }
}