package com.pxl.android.cemeo.core;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.pxl.android.cemeo.Injector;
import com.pxl.android.cemeo.R;
import com.pxl.android.cemeo.ui.CreateMeetingActivity;
import javax.inject.Inject;

import com.pxl.android.cemeo.util.Ln;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import static com.pxl.android.cemeo.core.Constants.Notification.TIMER_NOTIFICATION_ID;

public class CreateMeeting {



}
