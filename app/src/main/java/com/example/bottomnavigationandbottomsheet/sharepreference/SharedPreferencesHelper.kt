package com.example.bottomnavigationandbottomsheet.sharepreference

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class SharedPreferencesHelper @Inject constructor(@ApplicationContext context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        public const val PREFS_NAME = "your_app_prefs"
        public const val KEY_IS_LOGGED_IN = "is_logged_in"
        public const val KEY_USER_NAME = "user_name"
    }

    open var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

   open var userName: String?
        get() = prefs.getString(KEY_USER_NAME, null)
        set(value) = prefs.edit().putString(KEY_USER_NAME, value).apply()

    open fun clear() {
        prefs.edit().clear().apply()
    }
}