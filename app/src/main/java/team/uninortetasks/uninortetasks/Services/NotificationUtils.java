package team.uninortetasks.uninortetasks.Services;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import team.uninortetasks.uninortetasks.Activities.HomeScreen;
import team.uninortetasks.uninortetasks.R;

public class NotificationUtils {

    private static final String CHANNEL_ID = "TASKY-NOTIFICATION";
    private static final int NOTIFICATION_ID = 669272262;
    private static NotificationManagerCompat manager;

    public static void displayNotification(Context context) {
        if (manager == null) manager = NotificationManagerCompat.from(context);
        Intent i = new Intent(context, HomeScreen.class);
        TaskStackBuilder stack = TaskStackBuilder.create(context);
        stack.addNextIntentWithParentStack(i);
        PendingIntent pendingIntent = stack.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
        RemoteViews normal = new RemoteViews(context.getPackageName(), R.layout.notification_normal);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setCustomContentView(normal)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentIntent(pendingIntent)
                .setOngoing(true);
        manager.notify(NOTIFICATION_ID, notification.build());
    }

    public static void removeNotification(Context context) {
        if (manager == null) manager = NotificationManagerCompat.from(context);
        manager.cancel(NOTIFICATION_ID);
    }

}
