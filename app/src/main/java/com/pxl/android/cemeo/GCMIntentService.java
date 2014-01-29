package com.pxl.android.cemeo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;


import com.pxl.android.cemeo.ui.CarouselActivity;
import com.pxl.android.cemeo.util.Ln;


import net.jarlehansen.android.gcm.client.GCMUtils;
import net.jarlehansen.android.gcm.client.GCMUtilsBaseIntentService;
import net.jarlehansen.android.gcm.client.log.GCMUtilsLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GCMIntentService extends GCMUtilsBaseIntentService {


    @Override
    protected void onMessage(Context context, String msg) {

        Ln.d("statuslog: Message received: %s", msg);

        try {
            String result = get("http://cemeo.azurewebsites.net/api/Proposition/push");

            //Intent intent = new Intent(GCMUtilsActivity.MSG_ACTION);
            //intent.putExtra(GCMUtilsConstants.DATA_KEY_MSG, result);
            //context.sendBroadcast(intent);

            generateNotification(context, result);

        } catch (IOException e) {
            e.printStackTrace();
        }




    }



    @Override
    protected void onError(Context context, String error) {

        Ln.d("statuslog: Error : %s", error);

    }


    protected String get(String urlapi) throws IOException {
        URL url;
        try {
            url = new URL(urlapi);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + urlapi);
        }

        //Log.v(TAG, "****GET**** --->" + url);

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","");

            // get the request
            conn.connect();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                //Log.v(TAG, "inhoud " + conn.getResponseCode());
                throw new IOException("Get failed with error code " + status);
            }

            Ln.d("statuslog: inhoud: %s", conn.getResponseCode());
            String res;

            InputStream stream = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));

            res = br.readLine();

            Ln.d("statuslog: inhoud : %s", res);

            return res;

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

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
