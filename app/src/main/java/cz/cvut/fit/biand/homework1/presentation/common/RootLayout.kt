package cz.cvut.fit.biand.homework1.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun RootLayout(
    navController: NavController,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background,
    ) {
        Scaffold(
            modifier = modifier
                .statusBarsPadding(),
            bottomBar = {
                BottomNavigation(
                    navController = navController,
                )
            },
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .imePadding()
                        .navigationBarsPadding()
                ) {
                    content(innerPadding)
                }
            },
        )
    }
}