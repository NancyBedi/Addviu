package com.app.addviu

import android.app.Application
import android.content.Context
import com.app.addviu.data.DataManager
import com.app.addviu.data.helper.SHARED_PREFERENCE_BASE
import com.app.addviu.data.helper.SharedPrefsHelper
import com.app.addviu.data.retrofitRequest.ApiClient
import com.app.addviu.data.retrofitRequest.ServiceHandler

class AppController : Application() {

    var dataManager: DataManager? = null
        private set
    var sharedHelper: SharedPrefsHelper? = null
        private set


    override fun onCreate() {
        super.onCreate()
        instance = this

        sharedHelper = SharedPrefsHelper(getSharedPreferences(SHARED_PREFERENCE_BASE, Context.MODE_PRIVATE))

        dataManager = DataManager(sharedHelper!!, ServiceHandler(ApiClient(sharedHelper!!)))

    }

    companion object {
        var instance: AppController? = null
            private set

        operator fun get(context: Context): AppController {
            return context.applicationContext as AppController
        }
    }
}