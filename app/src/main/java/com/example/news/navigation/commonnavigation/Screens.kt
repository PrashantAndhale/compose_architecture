package com.example.news.navigation.commonnavigation

sealed class Screens(var route: String) {
    object NewsDetail : Screens("News Detail")
    object News : Screens("News")
}