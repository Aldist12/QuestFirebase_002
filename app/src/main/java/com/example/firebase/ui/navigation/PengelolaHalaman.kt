package com.example.firebase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.firebase.view.HomeScreen
import com.example.firebase.view.InsertMhsView

@Composable
fun PengelolaHalaman(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ){
        composable(DestinasiHome.route){
            HomeScreen(navigateToltemEntry = {
                navController.navigate(DestinasiInsert.route)
            })

        }
        composable(DestinasiInsert.route) {
            InsertMhsView(onBack = {
                navController.popBackStack()
            }, onNavigate = {
                navController.navigate(DestinasiHome.route)
            })
        }
    }
}