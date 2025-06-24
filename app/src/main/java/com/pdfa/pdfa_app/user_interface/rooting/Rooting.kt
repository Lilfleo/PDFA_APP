package com.pdfa.pdfa_app.user_interface.rooting


sealed class Screen(val rout: String) {
    object Home: Screen("home_screen")
    object Fridge: Screen("fridge_screen")
    object Recipe: Screen("recipe_screen")
    object Cookbook: Screen("cookbook_screen")
}

data class NavigationItem(
    val title: String,
    val route: String
)

val navigationItems = listOf(
    NavigationItem(
        title = "Home",
        route = Screen.Home.rout
    ),
    NavigationItem(
        title = "Fridge",
        route = Screen.Fridge.rout
    ),
    NavigationItem(
        title = "Recipe",
        route = Screen.Recipe.rout
    ),
    NavigationItem(
        title = "Cookbook",
        route = Screen.Cookbook.rout
    )
)

