import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@Composable
fun CityDialog(onDismiss: () -> Unit, onConfirm: (city: String) -> Unit) {
    val cityName = remember { mutableStateOf("") }

    Dialog (onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Adicionar cidade favorita:"
                    )
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "",
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }
                Spacer (modifier = Modifier.height(20.dp))
                OutlinedTextField (
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Nome da cidade") },
                    value = cityName.value,
                    onValueChange = { cityName.value = it }
                )
                Spacer (modifier = Modifier.height(20.dp))
                Button(
                    onClick = { onConfirm(cityName.value) },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                )
                {
                    Text(text = "OK")
                }

            }
        }
    }
}