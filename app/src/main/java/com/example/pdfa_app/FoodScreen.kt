import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.example.pdfa_app.ui.viewmodel.FoodViewModel
import com.example.pdfa_app.data.model.Food
import java.util.*

@Composable
fun FoodScreen(modifier: Modifier = Modifier, viewModel: FoodViewModel = hiltViewModel()) {
    val foodList by viewModel.foodList.collectAsState()

    var name by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Food Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = link,
            onValueChange = { link = it },
            label = { Text("Link") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (name.isNotBlank() && link.isNotBlank()) {
                    viewModel.addFood(
                        Food(
                            name = name,
                            link = link,
                            caloriesPerKg = 1000,
                            caloriesPerUnit = 100,
                            expirationTime = Date(System.currentTimeMillis() + 86400000) // +1 day
                        )
                    )
                    name = ""
                    link = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add Food")
        }

        Spacer(Modifier.height(16.dp))

        Text("Food List:", style = MaterialTheme.typography.titleMedium)
        foodList.forEach {
            Text("- ${it.name} (${it.caloriesPerUnit} cal/unit)", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
