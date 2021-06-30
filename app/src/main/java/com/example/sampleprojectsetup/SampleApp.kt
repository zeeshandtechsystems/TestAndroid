package com.example.sampleprojectsetup
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import com.example.sampleprojectsetup.data.remote.ApiClient
import com.example.sampleprojectsetup.data.remote.ApiInterface
import com.example.sampleprojectsetup.utilities.helper.TinyDB
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class SampleApp : Application() {
    private lateinit var appSharedPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private var SHARED_NAME = "com.example.sampleprojectsetup"


    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        initAppCenter()
        initPreferences()
        FirebaseApp.initializeApp(instance!!)
        configRetrofit()
       // getFcmTokken()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//            context.InternetSpeedChecker()
    }


    private fun initAppCenter(){

        AppCenter.start(
            this, "619f9558-0442-4a1f-848a-78f5cfb254bc",
            Analytics::class.java, Crashes::class.java
        )
    }
    private fun initPreferences() {
        this.appSharedPrefs = getSharedPreferences(SHARED_NAME, Activity.MODE_PRIVATE)
        this.prefsEditor = appSharedPrefs.edit()
        db = TinyDB(applicationContext)
    }

    override fun onLowMemory() {
        super.onLowMemory()

        Log.i(TAG, "Freeing memory ...")

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.v(TAG, "public void onTrimMemory (int level)")
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }


    companion object {
        lateinit var db: TinyDB

        @SuppressLint("StaticFieldLeak")
        private var instance: SampleApp? = null

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        private val TAG = SampleApp::class.java.name
        lateinit var apiService: ApiInterface

        private var parentJob = Job()
        private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
        val scope = CoroutineScope(coroutineContext)


        fun configRetrofit() {
            apiService = ApiClient.getClient()!!.create(ApiInterface::class.java)
        }


    }

    /*private fun getFcmTokken() {
        FirebaseInstanceId.getInstance().instanceId

            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                val tokken = task.result?.token
                db.putString(Constants.DEVICE_ID, tokken!!)
                Log.d("Token", "New Token : $tokken")

            })

    }*/
}