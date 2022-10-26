package work.raman.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

fun Context.showNotificationWithFullScreenIntent(
    isLockScreen: Boolean = false,
    channelId: String = CHANNEL_ID,
    title: String = "Title",
    description: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."

) {
    val builder = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(android.R.drawable.arrow_up_float)
        .setContentTitle(title)
        .setContentText(description)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setFullScreenIntent(getFullScreenIntent(isLockScreen), true)


    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    with(notificationManager) {
        buildChannel()

        val notification = builder.build()

        notify(0, notification)
    }
}

private fun NotificationManager.buildChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Example Notification Channel"
        val descriptionText = "This is used to demonstrate the Full Screen Intent"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        createNotificationChannel(channel)
    }
}

private fun Context.getFullScreenIntent(isLockScreen: Boolean): PendingIntent {
    val destination = if (isLockScreen)
        Notification::class.java
    else
        Notification::class.java
    val intent = Intent(this, destination)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        return PendingIntent.getActivity(
            this,
            0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    } else {
        return PendingIntent.getActivity(
            this,
            0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}

private const val CHANNEL_ID = "channelId"