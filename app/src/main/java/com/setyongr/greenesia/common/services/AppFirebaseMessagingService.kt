package com.setyongr.greenesia.common.services

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.setyongr.greenesia.views.MainActivity
import android.content.Context.NOTIFICATION_SERVICE
import android.app.NotificationManager
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Context
import android.support.v4.app.NotificationCompat
import com.setyongr.greenesia.R


class AppFirebaseMessagingService: FirebaseMessagingService() {
    val TAG = "FirebaseMsgService"

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d(TAG, "From ${p0.from}")

        if (p0.data.isNotEmpty()){
            Log.d(TAG, "Message data payload ${p0.data}")
        }

        if(p0.notification != null){
            Log.d(TAG, "Message Notif ${p0.notification.body}");
        }
    }

    fun sendNotification(messageBody:String){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.navigation_empty_icon)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

}