package com.example.budyymate.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "BootReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED -> {
                Log.d(TAG, "Boot completed or package replaced, rescheduling daily reminder")
                
                try {
                    val alarmScheduler = AlarmScheduler(context)
                    alarmScheduler.scheduleDailyReminder()
                    Log.d(TAG, "Daily reminder rescheduled after boot")
                } catch (e: Exception) {
                    Log.e(TAG, "Error rescheduling daily reminder after boot", e)
                }
            }
        }
    }
}
