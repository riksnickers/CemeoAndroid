package com.pxl.android.cemeo.gcm.sender;


import com.pxl.android.cemeo.gcm.log.GCMUtilsLog;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 10:37 AM
 */
public class DefaultGCMSenderCallback implements GCMSenderCallback {
    public void onRequestSent(GCMSenderResponse response) {
        GCMUtilsLog.i("Status from regIdSender: ", response.toString());
    }
}
