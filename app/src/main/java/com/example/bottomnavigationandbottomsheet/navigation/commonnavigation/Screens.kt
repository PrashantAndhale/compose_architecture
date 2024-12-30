package com.example.bottomnavigationandbottomsheet.navigation.commonnavigation

sealed class Screens(var route: String) {
    object Profile : Screens("Profile")
    object Showmax : Screens("Showmax-ATB")
    object Resume : Screens("Resume")
    object Calender : Screens("Calender")
    object Completed : Screens("Completed")
    object Sync : Screens("Sync")
    object Notification : Screens("Notification")
    object NewInstallation : Screens("New Installation")
    object MovieDetail : Screens("Movie Detail")
    object Movies : Screens("Movies")
    object Login : Screens("Login")
}