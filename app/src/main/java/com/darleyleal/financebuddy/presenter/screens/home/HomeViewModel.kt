package com.darleyleal.financebuddy.presenter.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darleyleal.financebuddy.data.local.Category
import com.darleyleal.financebuddy.data.local.Registration
import com.darleyleal.financebuddy.domain.usercases.RegistrationUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val registrationUserCase: RegistrationUserCase
) : ViewModel() {
    data class UiState(
        val name: String? = null,
        val description: String? = null,
        val value: String? = null,
        val date: String? = null,
        val type: String? = null,
        val category: Category? = null,
        val registrations: List<Registration> = emptyList(),
        val isLoading: Boolean = false
    )

    private val _uiState = MutableStateFlow(
        UiState(isLoading = true)
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        getAllRegistrations()
    }

    private fun getAllRegistrations() {
        viewModelScope.launch {
            registrationUserCase.getAllRegistrations().collect { registrations ->
                _uiState.update {
                    it.copy(
                        registrations = registrations
                    )
                }
            }
        }
    }
}