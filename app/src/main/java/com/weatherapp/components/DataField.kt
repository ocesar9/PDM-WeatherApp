import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

@Composable
fun DataField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onValueChange
    )
    Spacer(modifier = Modifier.size(24.dp))
}
