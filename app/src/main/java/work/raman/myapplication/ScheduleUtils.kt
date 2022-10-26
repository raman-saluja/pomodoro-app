package work.raman.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import java.util.concurrent.TimeUnit

fun Context.scheduleNotification(isLockScreen: Boolean) {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val timeInMillis = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(SCHEDULE_TIME)

    with(alarmManager) {
        setExact(AlarmManager.RTC_WAKEUP, timeInMillis, getReceiver(isLockScreen))
    }
}

private fun Context.getReceiver(isLockScreen: Boolean): PendingIntent {
    // for demo purposes no request code and no flags

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        return PendingIntent.getBroadcast(
            this,
            0,
            NotificationReceiver.build(this, isLockScreen),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    } else {
        return PendingIntent.getBroadcast(
            this,
            0,
            NotificationReceiver.build(this, isLockScreen),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

}

private const val SCHEDULE_TIME = 5L