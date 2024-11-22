package ua.asparian.practice5

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LossCalculator(navController: NavController) {
    var omega by remember { mutableStateOf("") } // Частота відмов
    var tb by remember { mutableStateOf("") }    // Середній час відновлення
    var pNom by remember { mutableStateOf("") }  // Номінальна потужність
    var tm by remember { mutableStateOf("") }    // Тариф
    var kp by remember { mutableStateOf("") }    // Коефіцієнт планового простою
    var zPer0 by remember { mutableStateOf("") } // Прямі збитки від аварійного відключення
    var zPlan by remember { mutableStateOf("") } // Прямі збитки від планового відключення

    var mWnedAvar by remember { mutableStateOf("") } // Аварійне недовідпущення
    var mWnedPlan by remember { mutableStateOf("") } // Планове недовідпущення
    var totalLosses by remember { mutableStateOf("") } // Загальні збитки

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Loss Calculator", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Поля для введення параметрів
        InputField(value = omega, label = "Failure Frequency (ω)") { omega = it }
        InputField(value = tb, label = "Average Recovery Time (t_b)") { tb = it }
        InputField(value = pNom, label = "Nominal Power (P_nom)") { pNom = it }
        InputField(value = tm, label = "Tariff (T_m)") { tm = it }
        InputField(value = kp, label = "Planned Downtime Coefficient (k_p)") { kp = it }
        InputField(value = zPer0, label = "Direct Losses (Z_per(0))") { zPer0 = it }
        InputField(value = zPlan, label = "Planned Losses (Z_per(plan))") { zPlan = it }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка для обчислення
        Button(
            onClick = {
                val omegaVal = omega.toDoubleOrNull() ?: 0.0
                val tbVal = tb.toDoubleOrNull() ?: 0.0
                val pNomVal = pNom.toDoubleOrNull() ?: 0.0
                val tmVal = tm.toDoubleOrNull() ?: 0.0
                val kpVal = kp.toDoubleOrNull() ?: 0.0
                val zPer0Val = zPer0.toDoubleOrNull() ?: 0.0
                val zPlanVal = zPlan.toDoubleOrNull() ?: 0.0

                // Проміжні обчислення
                val mwAvar = omegaVal * pNomVal * tbVal * tmVal
                println("MW недовідпущення (аварійне): $mwAvar")

                val mwPlan = kpVal * pNomVal * tmVal
                println("MW недовідпущення (планове): $mwPlan")

                val total = zPer0Val + (mwAvar * zPer0Val) + (mwPlan * zPlanVal)

                println("Прямі збитки (Z_per0): $zPer0Val")
                println("Збитки планового відключення (Z_plan): $zPlanVal")
                println("Загальні збитки: $total")

                mWnedAvar = "%.4f".format(mwAvar)
                mWnedPlan = "%.4f".format(mwPlan)
                totalLosses = "%.4f".format(total)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Виведення результатів
        DisplayLossResult("Expected Losses from Failures (M(W_нед.авар.))", mWnedAvar)
        DisplayLossResult("Expected Losses from Planned Downtime (M(W_нед.план.))", mWnedPlan)
        DisplayLossResult("Total Losses (Z_пер.)", totalLosses)

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Назад"
        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}

@Composable
fun InputField(value: String, label: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun DisplayLossResult(label: String, value: String) {
    Text(
        text = "$label: $value",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}
