package com.example.bottomnavigationandbottomsheet.screens.login

import android.app.Application
import com.example.bottomnavigationandbottomsheet.sharepreference.SharedPreferencesHelper

open class FakeSharedPreferencesHelper : SharedPreferencesHelper(Application()) {

    private val fakePrefs = mutableMapOf<String, Any?>()

    override var isLoggedIn: Boolean
        get() = fakePrefs[KEY_IS_LOGGED_IN] as? Boolean ?: false
        set(value) {
            fakePrefs[KEY_IS_LOGGED_IN] = value
        }

    override var userName: String?
        get() = fakePrefs[KEY_USER_NAME] as? String
        set(value) {
            fakePrefs[KEY_USER_NAME] = value
        }

    override fun clear() {
        fakePrefs.clear()
    }
}
