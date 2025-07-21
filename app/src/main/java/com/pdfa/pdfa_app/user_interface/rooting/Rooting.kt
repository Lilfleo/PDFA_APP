package com.pdfa.pdfa_app.user_interface.rooting


sealed class Screen(val rout: String) {
    object Home: Screen("home_screen")
    object Fridge: Screen("fridge_screen")
    object Shoplist: Screen("shoplist_screen")
    object Recipe: Screen("recipe_screen")
    object Cookbook: Screen("cookbook_screen")
    object Food: Screen("food_screen")
    object RecipeDetailScreen: Screen("recipe_main_page")
    object RecipeStepsScreen: Screen("recipe_steps_page")
}

data class NavigationItem(
    val title: String,
    val icon: AppIcons? = null,
    val route: String
)

val navigationItems = listOf(

    NavigationItem(
        title = "Shop-list",
        route = Screen.Shoplist.rout,
        icon = AppIcons.Shoplist
    ),
    NavigationItem(
        title = "Fridge",
        route = Screen.Fridge.rout,
        icon = AppIcons.Fridge
    ),
    NavigationItem(
        title = "Home",
        route = Screen.Home.rout,
        icon = AppIcons.HOME
    ),
    NavigationItem(
        title = "Recipe",
        route = Screen.Recipe.rout,
        icon = AppIcons.Recipe
    ),
    NavigationItem(
        title = "Cookbook",
        route = Screen.Cookbook.rout,
        icon = AppIcons.Cookbook
    ),

)
