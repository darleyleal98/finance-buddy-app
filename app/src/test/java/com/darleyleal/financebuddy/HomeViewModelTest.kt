package com.darleyleal.financebuddy

import android.content.Context
import com.darleyleal.financebuddy.domain.usercase.CategoryUserCase
import com.darleyleal.financebuddy.domain.usercase.RegistrationUserCase
import com.darleyleal.financebuddy.presetation.screens.home.HomeViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@HiltAndroidTest
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var registrationUserCase: RegistrationUserCase

    @Mock
    private lateinit var categoryUserCase: CategoryUserCase

    private lateinit var viewModel: HomeViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = HomeViewModel(registrationUserCase, categoryUserCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Verify if value for name field is updated`() {
        viewModel.updateName("Teste")
        val uiState = viewModel.uiState

        assertThat(uiState.value.name == "Teste").isTrue()
    }

    @Test
    fun `Validate forms field is not empty`() {

        viewModel.updateName("Darley")
        viewModel.updateDescription("Teste description")
        viewModel.updateValue("2025")
        viewModel.updateDate("26/01/2025")

        assertThat(viewModel.validateFormFields()).isTrue()
    }

    @Test
    fun `Verify if registrations list is empty`() {
        viewModel.getAllRegistrations()
        val uiState = viewModel.uiState

        assertThat(uiState.value.registrations).isEmpty()
    }
}