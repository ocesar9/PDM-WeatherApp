import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapp.db.FBDatabase
import com.weatherapp.models.City
import androidx.compose.runtime.remember
import com.weatherapp.repo.Repository


@Composable
fun ListPage(
    viewModel: MainViewModel,
    context: Context,
    repo: Repository,

    ) {
    val cities = viewModel.cities
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ){
        items(cities){
                city -> CityItem(
                        city = city,
                        onClick = {
                            Toast.makeText(context, "City added to your wish list", Toast.LENGTH_LONG).show()

                        },
                        onClose = {
                            repo.remove(city)
                            Toast.makeText(context, "City was removed from your wish list", Toast.LENGTH_LONG).show()
                        }
            )
        }
    }
}



@Composable

fun CityItem(
    city: City,
    onClick: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            Icons.Rounded.FavoriteBorder,
            contentDescription = "Favorite Border",
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)){
            Text(modifier = Modifier, text = city.name, fontSize = 24.sp)

        }
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}