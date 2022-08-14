package com.facundocetraro.deptchallenge.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import dagger.hilt.android.internal.Contexts.getApplication

class ConnectivityUtil {
    companion object {
        fun hasInternetConnection(context: Context): Boolean {
            val connectivityManager = getApplication(context).getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }

    }
}