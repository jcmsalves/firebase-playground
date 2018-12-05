package com.jcmsalves.firebaseplayground.cloudmessaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jcmsalves.firebaseplayground.MainActivity
import com.jcmsalves.firebaseplayground.R

class FirebasePlaygroundMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        createNotificationChannel()
        remoteMessage?.let {
            val customMessage = it.data.getValue(CUSTOM_KEY)
            val title = it.notification?.title?: "Backup title"
            val message = it.notification?.body?: "Backup message"
            sendNotification(title, "$message and $customMessage")
        }
    }

    private fun sendNotification(notifcationTitle: String, notificationContent: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_android_robot)
            .setContentTitle(notifcationTitle)
            .setContentText(notificationContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setChannelId(CHANNEL_ID)

        with(NotificationManagerCompat.from(this)) {
            notify(12345, notificationBuilder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, "channel name", importance)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "firebase_playground_channel_id"
        const val CUSTOM_KEY = "custom_key"
    }
}
