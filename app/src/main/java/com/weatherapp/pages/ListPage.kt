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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.weatherapp.R
import com.weatherapp.components.nav.BottomNavItem
import com.weatherapp.models.City
import com.weatherapp.repo.Repository


@Composable
fun ListPage(
    viewModel: MainViewModel,
    context: Context,
    repo: Repository,
    navController: NavHostController

) {
    val cities = viewModel.cities
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(cities) { city ->
            if (city.weather == null) {
                repo.loadWeather(city)
            }
            CityItem(
                city = city,
                onClick = {
                    viewModel.city = city
                    repo.loadForecast(city)
                    navController.navigate(BottomNavItem.HomePage.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                            restoreState = true

                        }
                        launchSingleTop = true
                    }

                },
                onClose = {
                    repo.remove(city)
                    Toast.makeText(
                        context,
                        "City was removed from your wish list",
                        Toast.LENGTH_LONG
                    ).show()
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
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = city.weather?.imgUrl,
            modifier = Modifier.size(75.dp),
            error = painterResource(id = R.drawable.loading_svgrepo_com),
            contentDescription = "image-content"
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(modifier = Modifier, text = city.name, fontSize = 24.sp)
            Text(
                modifier = Modifier,
                text = city.weather?.desc ?: "carregando...",
                fontSize = 16.sp
            )

        }
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}