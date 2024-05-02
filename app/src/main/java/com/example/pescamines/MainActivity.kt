package com.example.pescamines

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pescamines.ui.theme.PescaminesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                //color = background(colorResource(id = R.color.my_color))
            ) {
                Minesweeper()
            }
        }
    }
}
@Preview
@Composable
fun Minesweeper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("help") { HelpScreen(navController) }
        composable("config") { ConfigurationScreen() }
    }
}
@Composable
fun MainScreen(navController: NavController) {
    val activity = (LocalContext.current as? Activity)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = { navController.navigate("help") }) {
            Text("Com jugar")
        }
        Button(onClick = { navController.navigate("config") }) {
            Text("Nova partida")
        }
        Button(onClick = { activity?.finish() }) {
            Text("Sortir")
        }
    }
}
@Composable
fun HelpScreen(navController: NavController) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ){
        Text("Pàgina d'ajuda")
        Button(onClick = {navController.popBackStack()}) {
            Text("Tornar")
        }
    }
    
}
@Composable
fun ConfigurationScreen() {
    Text("Pàgina de configuració del joc")
}

