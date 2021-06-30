package com.example.sampleprojectsetup.utilities.fcm

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.sampleprojectsetup.SampleApp
import com.example.sampleprojectsetup.utilities.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*

/**
 * Develop By Messagemuse
 */
class MyFcmListenerService : FirebaseMessagingService() {

    private var TAG = "MyFirebaseMessagingService"
    private var broadcaster: LocalBroadcastManager? = null
    var counter = 0

    override fun onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this)
    }



    override fun onNewToken(tokken: String) {
        super.onNewToken(tokken)
        SampleApp.db.putString(Constants.DEVICE_ID, tokken!!)
        Log.d("Token", "New Token : $tokken")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "FROM : " + remoteMessage.from)
        //Verify if the message contains data
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data : " + remoteMessage.data)
            sendPush(remoteMessage.data, remoteMessage.notification)
        }
        //Verify if the message contains notification
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message body : " + remoteMessage.notification!!.body)
            //sendNotification(remoteMessage.data , remoteMessage.notification)
        }

    }


    private fun sendPush(data: MutableMap<String, String>, body: RemoteMessage.Notification?) {
/*        val count = EasyPeasyApp.db.getInt(Constants.CONST_NOTIFICATION_COUNT, 0)
        EasyPeasyApp.db.putInt(Constants.CONST_NOTIFICATION_COUNT, (count + 1))*/
        val mIntent = Intent("Notification")
        broadcaster?.sendBroadcast(mIntent)
        val rand = Random()
        counter = rand.nextInt(1000)

        /*if (data["type"].toString() == Constants.CONST_NOTIFICATION_TYPE_APP_UPDATE){
            try {
                (YNMSMain.mActivity as YNMSMain).getAppVersion()
            }catch (ex : Exception){
                ex.printStackTrace()
            }
        }*/
    }
}
