package com.example.commit.Singleton

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.content.Context

object GoogleAuthService {
    @SuppressLint("MissingPermission")
    fun getAccount(context: Context): String {
        // This call requires the Android GET_ACCOUNTS permission
        val accounts = AccountManager.get(context).getAccountsByType("com.google")
        return if (accounts.isEmpty()) {
            ""
        } else accounts[0].name
    }
}