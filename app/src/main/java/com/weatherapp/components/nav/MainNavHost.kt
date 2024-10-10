import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherapp.components.nav.BottomNavItem
import com.weatherapp.db.FBDatabase
import com.weatherapp.repo.Repository

@Composable
fun MainNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    repo: Repository,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.HomePage.route
    ) {
        composable(route = BottomNavItem.HomePage.route) {
            HomePage(
                viewModel = viewModel,
                repo = repo,
            )
        }
        composable(route = BottomNavItem.ListPage.route) {
            ListPage(
                viewModel = viewModel,
                context = context,
                repo = repo,
                navController = navController
            )
        }
        composable(route = BottomNavItem.MapPage.route) {
            MapPage(
                context = context,
                viewModel = viewModel,
                repo = repo
            )
        }
    }
}