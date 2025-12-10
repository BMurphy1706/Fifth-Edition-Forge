package com.example.ddraft

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ddraft.api.responses.ApiListItem
import com.example.ddraft.models.ApiDemoData
import com.example.ddraft.ui.views.ForgeScreen
import com.example.ddraft.ui.views.HomeScreen
import com.example.ddraft.ui.views.PortScreen
import com.example.ddraft.ui.theme.DDraftTheme
import com.example.ddraft.ui.views.DetailsScreen
import com.example.ddraft.ui.views.DiceScreen
import com.example.ddraft.viewModels.DemoVM
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
        //View Models

        val sharedVM = SharedVM()
        val portVM = PortViewModel()
        val dVM = DiceViewModel()
        setContent {
            val nc = rememberNavController()
            DDraftTheme {
                NavHost(nc, "home"){
                    composable("home"){ HomeScreen(sharedVM, nc) }
                    composable("forge"){ navBackStackEntry ->
                        val forgeVM: ForgeViewModel = hiltViewModel(navBackStackEntry)
                        ForgeScreen(forgeVM, sharedVM, nc) }
                    composable("port"){ PortScreen(portVM, sharedVM, nc) }
                    composable("dice"){ DiceScreen(dVM, sharedVM, nc) }
                    composable("details"){DetailsScreen(sharedVM, nc) }
                }
                //DemoScreen()
            }
        }
    }

    @Composable
    fun DemoScreen(
        mainViewModel: DemoVM = hiltViewModel()
    ) {
        val demoList = mainViewModel.demoList.value

        Surface(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(demoList) { demoData ->
                    PostItem(demoData)
                }
            }
        }

        val classList = mainViewModel.classList.value
        Log.d("C out", "$classList")
        Surface(modifier = Modifier.fillMaxSize()){
            LazyColumn {
                items(classList){ classData ->
                    ClassItem(classData)
                }
            }
        }
    }

    @Composable
    fun ClassItem(classData: ApiListItem){
        Card(Modifier.padding(8.dp)){
            Column(Modifier.padding(16.dp)){
                Text("Index: ${classData.index}")
                Text("Name: ${classData.name}")
                Text("url: ${classData.url}")
            }
        }
    }

    @Composable
    fun PostItem(postData: ApiDemoData) {
        Card(Modifier.padding(8.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text("Index: ${postData.index}")
                Text("Name: ${postData.name}")
                Text("Size: ${postData.size}")
                Text("Type: ${postData.type}")
                Text("Alignment: ${postData.alignment}")

                Spacer(Modifier.height(8.dp))

                Text("Armor Class:")
                postData.armor_class.forEach { ac ->
                    Text(" - Type: ${ac.type}, Value: ${ac.value}")
                }

                Spacer(Modifier.height(8.dp))

                Text("Speed:")
                Text(" - Walk: ${postData.speed.walk}")
                postData.speed.fly?.let { Text(" - Fly: $it") }
                postData.speed.swim?.let { Text(" - Swim: $it") }

                Spacer(Modifier.height(8.dp))

                Text("Proficiencies:")
                postData.proficiencies.forEach { prof ->
                    Text(" - ${prof.proficiency.name}: ${prof.value}")
                }
            }
        }
    }
}