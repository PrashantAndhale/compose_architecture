package com.example.bottomnavigationandbottomsheet.screens.login

import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.mockito.Mockito.mock

open class FakeSharedViewModel : SharedViewModel(mock(FakeSharedPreferencesHelper::class.java)) {
    private val testIsLogin = MutableStateFlow(false)

    override val isLogin: StateFlow<Boolean> get() = testIsLogin

    override fun setLogin(isLogin: Boolean) {
        testIsLogin.value = isLogin
    }
}