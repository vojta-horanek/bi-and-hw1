package cz.cvut.fit.biand.homework1.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import cz.cvut.fit.biand.homework1.presentation.common.RootLayout
import cz.cvut.fit.biand.homework1.presentation.navigation.AppNavigation
import cz.cvut.fit.biand.homework1.presentation.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                val navController = rememberNavController()
                RootLayout(
                    navController = navController,
                ) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}
