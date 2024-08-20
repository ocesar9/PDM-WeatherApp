import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherapp.components.nav.BottomNavItem
import com.weatherapp.db.FBDatabase

@Composable
fun MainNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    fbDB: FBDatabase,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.HomePage.route
    ) {
        composable(route = BottomNavItem.HomePage.route) {
            HomePage(
                viewModel = viewModel,
                context = context,
                fbDB = fbDB
            )
        }
        composable(route = BottomNavItem.ListPage.route) {
            ListPage(
                viewModel = viewModel,
                context = context,
                fbDB = fbDB
            )
        }
        composable(route = BottomNavItem.MapPage.route) {
            MapPage(
                context = context,
                viewModel = viewModel,
                fbDB = fbDB
            )
        }
    }
}