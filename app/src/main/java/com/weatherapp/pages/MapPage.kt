import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.weatherapp.R

@Composable
fun MapPage(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    context: Context
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

        GoogleMap (
            cameraPositionState = camPosState,
            modifier = Modifier.fillMaxSize(),
            onMapClick = {

                viewModel.add("Nova Cidade", location = it)
            }
        ) {
            viewModel.cities.forEach {
                if(it.location != null){
                    Marker(
                        state = MarkerState(position = it.location!!),
                        title = it.name,
                        snippet = "${it.location}"
                    )
                }
            }
            Marker(
                state = MarkerState(position = recife),
                title = "Recife",
                snippet = "Marcador em Recife",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
            Marker(
                state = MarkerState(position = caruaru),
                title = "Caruaru",
                snippet = "Marcador em Caruaru",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
            Marker(
                state = MarkerState(position = joaopessoa),
                title = "João Pessoa",
                snippet = "Marcador em João Pessoa",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )

        }

    }
}