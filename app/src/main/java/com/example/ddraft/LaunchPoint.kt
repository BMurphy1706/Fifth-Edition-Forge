package com.example.ddraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ddraft.ui.theme.DDraftTheme
import com.example.ddraft.ui.views.ForgeScreen
import com.example.ddraft.ui.views.HomeScreen
import com.example.ddraft.ui.views.PortScreen
import com.example.ddraft.ui.views.DiceScreen
import com.example.ddraft.ui.views.DetailsScreen
import com.example.ddraft.viewModels.DiceViewModel
import com.example.ddraft.viewModels.ForgeViewModel
import com.example.ddraft.viewModels.PortViewModel
import com.example.ddraft.viewModels.SharedVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchPoint : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val nc = rememberNavController()
            DDraftTheme {
                Surface(modifier = Modifier.padding(0.dp)) {
                    val sharedVM: SharedVM = hiltViewModel()

                    NavHost(nc, "home") {
                        composable("home") {
                            sharedVM.onNavIndexChange(0)
                            HomeScreen(sharedVM, nc)
                        }
                        composable("forge") { navBackStackEntry ->
                            val forgeVM: ForgeViewModel = hiltViewModel(navBackStackEntry)
                            sharedVM.onNavIndexChange(1)
                            ForgeScreen(forgeVM, sharedVM, nc)
                        }
                        composable("port") {
                            val portVM: PortViewModel = hiltViewModel()
                            sharedVM.onNavIndexChange(3)
                            PortScreen(portVM, sharedVM, nc)
                        }
                        composable("dice") {
                            val dVM: DiceViewModel = hiltViewModel()
                            sharedVM.onNavIndexChange(2)
                            DiceScreen(dVM, sharedVM, nc)
                        }
                        composable("details") {
                            sharedVM.onNavIndexChange(0)
                            DetailsScreen(sharedVM, nc)
                        }
                    }
                }
            }
        }
    }
}