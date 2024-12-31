package com.darleyleal.financebuddy.presenter.screens.start

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.presenter.theme.FinanceBuddyTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartScreen(
    navigationProvider: NavigationProvider,
    modifier: Modifier = Modifier,
    onNavigateToHomeScreen: () -> Unit
) {
    FinanceBuddyTheme {

        val startViewModel = navigationProvider.getViewModel(ViewModelKey.START) as StarViewModel
        val isAuthenticated by startViewModel.isAuthenticated.collectAsState()

        if (isAuthenticated) {
            onNavigateToHomeScreen()
        }

        Scaffold {
            Column(
                modifier = modifier
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0xFF181023),
                                Color(0xFF3C2656),
                                Color(0xFF00EEFF)
                            )
                        )
                    )
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.financebuddyicon),
                    contentDescription = null,
                    tint = Color(0xFF00EEFF),
                    modifier = modifier.size(145.dp)
                )
            }
        }
    }
}