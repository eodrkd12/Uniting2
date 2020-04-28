package com.example.commit.Class

import android.app.Activity
import android.app.ActivityManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.commit.MainActivity.MainActivity
import com.example.commit.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage



class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
    }


    override fun onMessageReceived(p0: RemoteMessage) {
        if(p0.notification!=null){
            if(!isAppOnForeground(this))
                sendNotification(p0.to,p0.notification?.title,p0.notification?.body)
        }
    }

    private fun sendNotification(to:String?,title: String?,body: String?) {
        Log.d("uniting","${to}")
        val intent=Intent(this,MainActivity::class.java).apply {
            flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("Notification",body)
        }

        var pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
        val notificationSound=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var notificationBuilder=NotificationCompat.Builder(this,"fcm_uniting")
            .setSmallIcon(R.mipmap.ic_launcher) // 로고
            .setContentTitle("테스트")
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(notificationSound)
            .setContentIntent(pendingIntent)

        var notificationManager: NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())

    }

    private fun isAppOnForeground(context: Context) : Boolean{
        var activityManager=context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var appProcesses=activityManager.runningAppProcesses
        if(appProcesses==null) return false
        val packageName=context.packageName
        for(appProcess in appProcesses){
            if(appProcess.importance==ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)){
                return true
            }
        }

        return false
    }

}