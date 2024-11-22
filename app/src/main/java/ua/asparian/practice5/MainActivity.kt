package ua.asparian.practice5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppNavigation()
        }
    }
}

@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_menu") {
        composable("main_menu") { MainMenu(navController) }
        composable("reliability_calculator") { ReliabilityCalculator(navController) } // Передаємо navController
        composable("loss_calculator") { LossCalculator(navController) } // Передаємо navController
    }
}

@Composable
fun MainMenu(navController: NavHostController) {
    Surface {
        Column {
            Button(onClick = { navController.navigate("reliability_calculator") }) {
                Text("Reliability Calculator")
            }
            Button(onClick = { navController.navigate("loss_calculator") }) {
                Text("Loss Calculator")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    val navController = rememberNavController()
    MainMenu(navController)
}
