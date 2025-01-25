package com.darleyleal.financebuddy.presetation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.data.models.Registration
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.presetation.navigation.NavigationProvider
import com.darleyleal.financebuddy.presetation.components.HistoryInformations
import com.darleyleal.financebuddy.presetation.components.OpenInFullScreenRegistrationModalSheet
import com.darleyleal.financebuddy.presetation.components.RemoveItemDialog
import com.darleyleal.financebuddy.presetation.screens.home.card_information.CardInformation
import com.darleyleal.financebuddy.presetation.screens.home.card_information.CardInformationViewModel
import com.darleyleal.financebuddy.presetation.screens.home.update_registration.UpdateRegistrationModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigationProvider: NavigationProvider,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val homeViewModel = navigationProvider.getViewModel(ViewModelKey.HOME) as HomeViewModel
    val homeViewModelUiState by homeViewModel.uiState.collectAsState()

    val cardInformationViewModel = navigationProvider.getViewModel(
        ViewModelKey.BALANCE
    ) as CardInformationViewModel

    var cardValuesIsVisible by remember { mutableStateOf(true) }
    var deleteRegistrationItemSelected by remember { mutableStateOf<Registration?>(null) }
    var removeItem by remember { mutableStateOf(false) }
    var showBottonSheet by remember { mutableStateOf(false) }

    var openInFullScreenRegistrationModalSheet by remember { mutableStateOf(false) }
    var registrationToOpenInFullScreen by remember { mutableStateOf<Registration?>(null) }
    val uiState by cardInformationViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.balance?.id) {
        cardInformationViewModel.convertAvailableBalanceToCurrency()
        cardInformationViewModel.calculateValueAllExpenses()
        cardInformationViewModel.calculateValueAllIncomes()
    }

    Column(modifier.padding(paddingValues)) {
        CardInformation(
            availableBalance = cardInformationViewModel.convertAvailableBalanceToCurrency(),
            onClickVisibilityButton = {
                cardValuesIsVisible = !cardValuesIsVisible
            },
            income = cardInformationViewModel.calculateValueAllIncomes(),
            expanse = cardInformationViewModel.calculateValueAllExpenses(),
            modifier = modifier,
            valuesIsVisible = cardValuesIsVisible,
        )

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(32.dp),
            text = stringResource(R.string.my_history),
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            color = when {
                isSystemInDarkTheme() -> {
                    Color.Cyan
                }

                else -> {
                    Color.Black
                }
            }
        )

        when {
            homeViewModelUiState.registrations.isEmpty() -> {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = stringResource(R.string.no_registrations_found),
                    textAlign = TextAlign.Center
                )
            }

            else -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(300.dp),
                    content = {
                        items(homeViewModelUiState.registrations) { item ->
                            HistoryInformations(registration = item,
                                onDeleteItem = { registration ->
                                    deleteRegistrationItemSelected = registration
                                },
                                registrationId = { id ->
                                    homeViewModel.getRegistrationById(id)
                                },
                                showDeleteDialog = {
                                    removeItem = it
                                },
                                showUpdateRegistrationButtonSheet = {
                                    showBottonSheet = it
                                },
                                openInFullModalSheetButton = { state, registration ->
                                    openInFullScreenRegistrationModalSheet = state
                                    registrationToOpenInFullScreen = registration
                                }
                            )
                        }
                    }
                )
            }
        }

        when {
            removeItem -> {
                RemoveItemDialog(
                    onDismissRequest = {
                        removeItem = !removeItem
                    },
                    onConfirmation = {
                        deleteRegistrationItemSelected?.let { homeViewModel.delete(it) }
                    },
                    dialogTitle = stringResource(R.string.are_you_sure_about_this),
                    dialogText = stringResource(R.string.delete_item),
                )
            }

            showBottonSheet -> {
                homeViewModelUiState.registration?.let {
                    UpdateRegistrationModalBottomSheet(
                        showBottonSheet = showBottonSheet,
                        updateStateBottonSheet = { showBotton ->
                            showBottonSheet = showBotton
                        },
                        viewModel = homeViewModel,
                        registration = it,
                        onUpdateRegistrationClick = {
                            homeViewModel.updateRegistration(
                                homeViewModelUiState.id,
                                homeViewModelUiState.name,
                                homeViewModelUiState.description,
                                homeViewModelUiState.value,
                                homeViewModelUiState.date,
                                homeViewModelUiState.category,
                                homeViewModelUiState.type
                            )
                            showBottonSheet = false
                        }
                    )
                }
            }

            openInFullScreenRegistrationModalSheet -> {
                registrationToOpenInFullScreen?.let {
                    OpenInFullScreenRegistrationModalSheet(
                        modifier = modifier,
                        registration = it,
                        showModalBottomSheet = { state ->
                            openInFullScreenRegistrationModalSheet = state
                        }
                    )
                }
            }
        }
    }
}