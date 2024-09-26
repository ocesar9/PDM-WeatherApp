import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import androidx.core.graphics.scale
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.weatherapp.R
import com.weatherapp.repo.Repository

@Composable
fun MapPage(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    context: Context,
    repo: Repository

) {
    val camPosState = rememberCameraPositionState()


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_700))
            .wrapContentSize(Alignment.Center)
    ) {
        val recife = LatLng(-8.05, -34.9);
        val caruaru = LatLng(-8.27, -35.98);
        val joaopessoa = LatLng(-7.12, -34.84);

        val hasLocationPermission by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            )
        }

        GoogleMap(
            cameraPositionState = camPosState,
            modifier = Modifier.fillMaxSize(),
            onMapClick = {

                repo.addCity(lat = it.latitude, lng = it.longitude)


            },
            properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
            uiSettings = MapUiSettings(myLocationButtonEnabled = true)
        ) {
            viewModel.cities.forEach { city ->
                if (city.location != null) {
                    var marker = BitmapDescriptorFactory.defaultMarker()
                    if (city.weather == null) {
                        repo.loadWeather(city)
                    } else if (city.weather!!.bitmap == null) {
                        repo.loadBitmap(city)
                    } else {
                        marker = BitmapDescriptorFactory
                            .fromBitmap(city.weather!!.bitmap!!.scale(200, 200)
                        )
                    }
                    Marker (
                        state = MarkerState(position = city.location!!), icon = marker, title = city.name,
                        snippet = city.weather?.desc ?: "loading..."
                    )
                }
            }
        }

    }
}