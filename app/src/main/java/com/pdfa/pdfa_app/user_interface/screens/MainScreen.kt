import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pdfa.pdfa_app.api.Ingredient
import com.pdfa.pdfa_app.api.RecipeForShoplist
import com.pdfa.pdfa_app.api.RecipeWithFood
import com.pdfa.pdfa_app.api.RecipeWithFoodPrompt
import com.pdfa.pdfa_app.api.RecipeWithoutFoodPrompt
import com.pdfa.pdfa_app.api.Tags
import com.pdfa.pdfa_app.data.model.Profil
import com.pdfa.pdfa_app.ui.viewmodel.ProfilViewModel
import com.pdfa.pdfa_app.ui.viewmodel.RecipeViewModel
import com.pdfa.pdfa_app.user_interface.component.BottomNavBar
import com.pdfa.pdfa_app.user_interface.component.DrawerMenu
import com.pdfa.pdfa_app.user_interface.component.TopBar
import com.pdfa.pdfa_app.user_interface.rooting.Screen
import com.pdfa.pdfa_app.user_interface.screens.CookbookScreen
import com.pdfa.pdfa_app.user_interface.screens.RecipeScreen
import com.pdfa.pdfa_app.user_interface.screens.FridgeScreen
import com.pdfa.pdfa_app.user_interface.screens.HomeScreen
import kotlinx.coroutines.launch
import com.pdfa.pdfa_app.user_interface.rooting.navigationItems
import com.pdfa.pdfa_app.user_interface.screens.ProfilScreen
import com.pdfa.pdfa_app.user_interface.screens.RecipeDetailScreen
import com.pdfa.pdfa_app.user_interface.screens.RecipeStepsScreen
import com.pdfa.pdfa_app.user_interface.screens.ShoplistScreen


@Composable
fun MainScreen() {

    val recipViewModel: RecipeViewModel = hiltViewModel()

    //API
    LaunchedEffect(Unit) {
        recipViewModel.launchRecipeWithFoodCall()
        recipViewModel.launchRecipeWithoutFood()
    }

//    val profilViewModel: ProfilViewModel = hiltViewModel()
//    val profil: Profil = Profil(name = "Antoine", age = 25)
//    LaunchedEffect(Unit) {
//        profilViewModel.insertProfil(profil)
//    }

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedIndex = rememberSaveable { mutableIntStateOf(2) }

    //val navigationItems = listOf("Home", "Fridge", "Recipe", "Cookbook") // ou ta version NavigationItem

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val hideBottomBarRoutes = listOf(
        Screen.RecipeDetailScreen.rout,
        Screen.RecipeStepsScreen.rout,
        Screen.ProfilScreen.rout
    )
    val showBackButtonRoutes = listOf(
        Screen.RecipeDetailScreen.rout,
        Screen.RecipeStepsScreen.rout,
        Screen.ProfilScreen.rout
    )

    ModalNavigationDrawer(

        drawerState = drawerState,
        drawerContent = {
            DrawerMenu(
                navController = navController,
                drawerState = drawerState)
        }

    ) {
        Scaffold(
            topBar = {
                TopBar(
                    showBackButton = currentRoute in showBackButtonRoutes,
                    onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            },
            bottomBar = {
                if (currentRoute !in hideBottomBarRoutes) {
                    BottomNavBar(
                        selectedIndex = selectedIndex.intValue,
                        onItemSelected = { index, route ->
                            selectedIndex.intValue = index
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        items = navigationItems
                    )
                }

            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.rout, // ← "home_screen"
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                composable(Screen.Home.rout) { HomeScreen() }
                composable(Screen.Fridge.rout) {
                    FridgeScreen(onAddClick = {
                        println("Bouton Add cliqué") })
                }
                composable(Screen.Shoplist.rout){ ShoplistScreen() }
                composable(Screen.Recipe.rout) { RecipeScreen(navController, recipViewModel) }
                composable(Screen.Cookbook.rout) { CookbookScreen(navController) }
                composable (Screen.Food.rout ){ FoodScreen() }
                composable(Screen.RecipeDetailScreen.rout) { RecipeDetailScreen(navController, recipViewModel) }
                composable(Screen.RecipeStepsScreen.rout) {RecipeStepsScreen( navController, recipViewModel)}
                composable(Screen.ProfilScreen.rout) { ProfilScreen(navController)}
            }
        }
    }
}


