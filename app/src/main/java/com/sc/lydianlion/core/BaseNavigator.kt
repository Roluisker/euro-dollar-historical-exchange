package com.sc.lydianlion.core

import androidx.navigation.NavController

open class BaseNavigator {

    private var navController: NavController? = null

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun unbind() {
        navController = null
    }

}