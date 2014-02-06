package com.pxl.android.cemeo.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.google.android.gcm.GCMBaseIntentService;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.gcm.GCMUtilsConstants;
import com.pxl.android.cemeo.gcm.log.GCMUtilsLog;
import com.pxl.android.cemeo.ui.CarouselActivity;
import com.pxl.android.cemeo.util.Ln;

/**
 * Provides a few simple helper methods. The {@link #onMessage(android.content.Context, String)} simply parses String messages sent with the {@code GCMUtilsConstants.DATA_KEY_MSG}.
 * <p/>
 * {@link #onRegistered(android.content.Context, String)} and {@link #onUnregistered(android.content.Context, String)} will automatically send requests to the receiving server (read {@code receiver-url} from gcmutils.properties).
 * If this behavior is unwanted, simply override the methods.
 * <p/>
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 12:09 PM
 */
public abstract class GCMUtilsBaseIntentService extends GCMBaseIntentService {

    protected GCMUtilsBaseIntentService() {
    }

    protected GCMUtilsBaseIntentService(String... senderIds) {
        super(senderIds);
    }
/*
    @Override
    protected void onMessage(Context context, Intent intent) {
        GCMUtilsLog.i("onMessage -->");
        if (intent.hasExtra(GCMUtilsConstants.DATA_KEY_MSG)) {
            String msg = intent.getStringExtra(GCMUtilsConstants.DATA_KEY_MSG);
            GCMUtilsLog.i("msg received: ", msg);
            onMessage(context, msg);
        }
    }
*/


    protected void onMessage(Context context, String msg) {

        //Ln.d("statuslog: Message received: %s", msg);

        //String result = get("http://cemeo.azurewebsites.net/api/Proposition/push");

        //String result = get("http://app.cemeo.be/api/Proposition/push");
        //Intent intent = new Intent(GCMUtilsActivity.MSG_ACTION);
        //intent.putExtra(GCMUtilsConstants.DATA_KEY_MSG, result);
        //context.sendBroadcast(intent);

        //generateNotification(context, msg);






    }


    @Override
    protected void onRegistered(Context context, String regId) {
        GCMUtilsLog.i("onRegistered, regId=", regId);
        GCMUtils.createRegAndEmailSender(context, regId).send();
    }

    @Override
    protected void onUnregistered(Context context, String regId) {
        GCMUtilsLog.i("onUnregistered, regId=", regId);
        GCMUtils.createUnregAndEmailSender(context, regId).send();
    }


    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {

        Intent notificationIntent = new Intent(context, CarouselActivity.class);

        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(context,0 , notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.cemeo_notification))
                .setTicker(res.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(res.getString(R.string.app_name))
                .setVibrate(new long[]{100,200,100,500})
                .setPriority(Notification.PRIORITY_MAX)
                .setLights(0xff00ff00, 300, 100)
                .setContentText(message);
        Notification n = builder.build();


        nm.notify(0, n);







    }

}
