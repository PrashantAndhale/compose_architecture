package com.example.bottomnavigationandbottomsheet.screens.profile

import com.example.domain.model.MoviesItem

data class ProfileState(
    var isLoading: Boolean = false,
    var data: List<MoviesItem>? = null,
    var error: String = ""
)