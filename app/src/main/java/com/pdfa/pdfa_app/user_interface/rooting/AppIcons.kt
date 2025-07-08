package com.pdfa.pdfa_app.user_interface.rooting

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.painter.Painter
import com.pdfa.pdfa_app.R

enum class AppIcons(@DrawableRes val drawableRes: Int) {
    HOME(R.drawable.ic_nav_home),
    Fridge(R.drawable.ic_nav_fridge),
    Recipe(R.drawable.ic_nav_recipe),
    Cookbook(R.drawable.ic_nav_cookbook),
    Shoplist(R.drawable.ic_nav_shoplist),

    Bag(R.drawable.ic_nav_bag)
}

@Composable
fun AppIcons.painter(): Painter {
    return painterResource(id = this.drawableRes)
}
