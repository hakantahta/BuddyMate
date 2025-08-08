package com.example.budyymate.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DailyReminderReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "DailyReminderReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Daily reminder received")
        
        try {
            val notificationHelper = NotificationHelper(context)
            notificationHelper.showDailyReminderNotification()
            Log.d(TAG, "Daily reminder notification sent successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error showing daily reminder notification", e)
        }
    }
}
