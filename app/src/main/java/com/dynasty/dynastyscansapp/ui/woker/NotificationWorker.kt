package com.dynasty.dynastyscansapp.ui.woker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import com.dynasty.dynastyscansapp.data.repository.ServiceRepository
import com.dynasty.dynastyscansapp.ui.MainActivity
import com.dynasty.dynastyscansapp.ui.chapter.ChapterActivity
import com.dynasty.dynastyscansapp.utils.Preferences
import kotlinx.coroutines.*
import okhttp3.internal.notify
import org.koin.core.KoinComponent
import org.koin.core.get

import java.lang.Exception

class NotificationWorker(
    context: Context,
    parameters: WorkerParameters
) : CoroutineWorker(context, parameters), CoroutineScope, KoinComponent {

    private val CHANNEL_ID = "1150"

    private val GROUP_KEY_WORK = "com.dynasty.dynastyscansapp.ui.woker"

    override suspend fun doWork(): Result = coroutineScope {
        val repository: ServiceRepository = get()
        try {
            val newChapters = repository.getChapterList(1)
            val latestChapter = Preferences.latestChapter
            if (latestChapter != null && newChapters != latestChapter) {
                newChapters.chapters?.filter {
                    latestChapter.chapters?.run {
                        !contains(it)
                    } ?: false
                }?.forEach {
                    showNotification(it)
                }

                Preferences.latestChapter = newChapters
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private fun showNotification(it: ChapterModel) {
        val chapterIntent = ChapterActivity.getIntent(applicationContext, it)
        val chapterPendingIntent: PendingIntent? = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(chapterIntent)
            getPendingIntent(System.currentTimeMillis().toInt(), PendingIntent.FLAG_CANCEL_CURRENT)
        }

        val newNotification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(it.series ?: it.title)
            .setContentText(it.title)
            .setGroup(GROUP_KEY_WORK)
            .setAutoCancel(true)
            .setContentIntent(chapterPendingIntent)
            .build()

        val intent = Intent(applicationContext, MainActivity::class.java)
        val contentIntent: PendingIntent? = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT
        )

        val groupNotificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText("New Chapter")
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .setGroup(GROUP_KEY_WORK)
            .setGroupSummary(true)
            .setContentIntent(contentIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(System.currentTimeMillis().toInt(), newNotification)
        notificationManager.notify(0, groupNotificationBuilder)
    }
}