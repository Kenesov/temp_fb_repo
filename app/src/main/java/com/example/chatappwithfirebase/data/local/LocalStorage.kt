package com.example.chatappwithfirebase.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.chatappwithfirebase.app.App
import com.example.chatappwithfirebase.utils.IntPreference
import com.example.chatappwithfirebase.utils.StringPreference

class LocalStorage {

    companion object {
        val pref =
            App.instance.getSharedPreferences("ChatAppSharedPref", Context.MODE_PRIVATE)
    }

    var username by StringPreference(pref, "temp001")
}