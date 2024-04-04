import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherapp.components.nav.BottomNavItem

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(
        navController,
        startDestination = BottomNavItem.HomePage.route
    ) {
        composable (route = BottomNavItem.HomePage.route) { HomePage() }
        composable (route = BottomNavItem.ListPage.route) { ListPage() }
        composable (route = BottomNavItem.MapPage.route) { MapPage() }
    }
}