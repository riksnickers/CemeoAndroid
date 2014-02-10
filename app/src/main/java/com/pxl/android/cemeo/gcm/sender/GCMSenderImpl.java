package com.pxl.android.cemeo.gcm.sender;

import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.pxl.android.cemeo.BootstrapApplication;
import com.pxl.android.cemeo.BootstrapServiceProvider;
import com.pxl.android.cemeo.authenticator.ApiKeyProvider;
import com.pxl.android.cemeo.core.BootstrapService;
import com.pxl.android.cemeo.core.Constants;
import com.pxl.android.cemeo.core.RegisterDevice;
import com.pxl.android.cemeo.gcm.GCMUtils;
import com.pxl.android.cemeo.gcm.log.GCMUtilsLog;
import com.pxl.android.cemeo.ui.BootstrapActivity;
import com.pxl.android.cemeo.ui.CarouselActivity;
import com.pxl.android.cemeo.util.Ln;

import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static com.pxl.android.cemeo.core.Constants.Http.URL_AUTH;
import static com.pxl.android.cemeo.core.Constants.Http.URL_CREATE_MEETING;
import static com.pxl.android.cemeo.core.Constants.Http.URL_REGISTER;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 9:00 AM
 */
public class GCMSenderImpl extends AsyncTask<Void, Void, GCMSenderResponse> implements GCMSender {

    private static final int BACKOFF_MILLIS = 2000;
    private static final int NUM_OF_RETRIES = 5;

    private final URL receiverUrl;
    private final List<BasicNameValuePair> requestParams;

    private GCMSenderCallback callback = new DefaultGCMSenderCallback();
    private int backoffMillis = BACKOFF_MILLIS;
    private int retries = NUM_OF_RETRIES;
    private Context context = null;
    private String json;


    public GCMSenderImpl(String receiverUrl, List<BasicNameValuePair> requestParams) {
        try {
            this.receiverUrl = new URL(receiverUrl);
        } catch (MalformedURLException mal) {
            throw new IllegalArgumentException("The receiver URL is invalid: " + receiverUrl, mal);
        }

        this.requestParams = requestParams;
    }

    public GCMSender setCallback(GCMSenderCallback callback) {
        this.callback = callback;
        return this;
    }

    public GCMSender setBackoffMillis(int millis) {
        this.backoffMillis = millis;
        return this;
    }

    public GCMSender setRetries(int retries) {
        this.retries = retries;
        return this;
    }

    public GCMSender enableResultToast(Context context) {
        this.context = context;
        return this;
    }

    public void send() {
        if (requestParams.size() == 0) {
            throw new IllegalArgumentException("There are no arguments sent to the GCMSender");
        } else {
            GCMUtilsLog.i("Sending request, backoff=" + backoffMillis, "ms retries=" + retries);
            AsyncTask<Void, Void, GCMSenderResponse> returnedValue = this.execute();

            try {
                callback.onRequestSent(returnedValue.get());
            } catch (InterruptedException ie) {
                GCMUtilsLog.e("AsyncTask thread was interrupted while waiting", ie);
            } catch (ExecutionException ee) {
                GCMUtilsLog.e("AsyncTask computation threw an exception during execution", ee);
            }
        }
    }

    @Override
    protected GCMSenderResponse doInBackground(Void... voids) {
        int backoff = backoffMillis;

        GCMSenderResponse errorResponse = null;
        for (int counter = 0; counter <= retries; counter++) {
            try{
            GCMSenderResponse response = sendRequest();
            if (response.ok() || response.almostOk())
                return response;
            else if (counter == retries)
                errorResponse = response;

            }catch (Exception e){
                GCMUtilsLog.w("Exception caught : No connection to the server", e);
            }

            try {
                Thread.sleep(backoff);
            } catch (InterruptedException ie) {
                GCMUtilsLog.w("Thread interrupted when waiting for new retry", ie);
            }

            if (counter > 0)
                backoff *= 2;

            if (counter < retries)
                GCMUtilsLog.i("Retry number " + (counter + 1), ", waiting for: " + backoff, "ms");
        }

        return errorResponse;
    }

    @Override
    protected void onPostExecute(GCMSenderResponse GCMSenderResponse) {
        if (context != null)
            Toast.makeText(context, GCMSenderResponse.toString(), Toast.LENGTH_SHORT).show();
    }

    private GCMSenderResponse sendRequest() {


            String key = "Bearer " + GCMUtils.getAuthKey();
            GCMUtilsLog.i("Key:", key, " code:" + key);

            String json = createRequestBody();

            if(json != null){
            HttpRequest request = HttpRequest.post(URL_REGISTER).header("Authorization", key).header("Content-Type" , "application/json").send(json).connectTimeout(5000);

            //Ln.d("statuslog: %s" , request.body());
            Ln.d("statuslog: %s" , request.code());

            return new GCMSenderResponse(request.code(), null);
            }
        return null;




    }
    /*

    private GCMSenderResponse sendRequest() {
        HttpURLConnection connection = null;
        try {
            byte[] contentBody = createRequestBody().getBytes();
            connection = (HttpURLConnection) receiverUrl.openConnection();
            connection.setDoOutput(true);
            //connection.setUseCaches(false);
            connection.setFixedLengthStreamingMode(contentBody.length);
            connection.setConnectTimeout(20000);

            String key = "Bearer " + GCMUtils.getAuthKey();
            GCMUtilsLog.i("Key:", key, " code:" + key);

            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", key );


            OutputStream outputStream = connection.getOutputStream();

            outputStream.write(contentBody);

            int responseCode = connection.getResponseCode();
            GCMUtilsLog.i("Response code:" + responseCode);
            //String responseMessage = connection.getResponseMessage();
            //GCMUtilsLog.i("Response message:", responseMessage, " code:" + responseCode);

            return new GCMSenderResponse(responseCode, null);
        } catch (IOException io) {
            String errorMsg = "Unable to send registration request to: " + receiverUrl;
            GCMUtilsLog.e(errorMsg, io);
            return new GCMSenderResponse(errorMsg, io);
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }
*/
    String createRequestBody() {
        //StringBuilder requestBody = new StringBuilder();
/*
        for (int i = 0; i < requestParams.size(); i++) {
            BasicNameValuePair requestParam = requestParams.get(i);
            requestBody.append(requestParam.getName()).append("=").append(requestParam.getValue());

            if (i < (requestParams.size() - 1))
                requestBody.append("&");
        }
*/
        BasicNameValuePair requestParam = requestParams.get(0);

        //json van maken
        RegisterDevice regdev = new RegisterDevice();
        regdev.setPlatform(1);
        regdev.setDeviceID(requestParam.getValue());

        Gson gson = new Gson();
        json = gson.toJson(regdev);

        String requestBodyString = json;
        GCMUtilsLog.i("Request body: ", requestBodyString);

        //String requestBodyString = requestBody.toString();
        //GCMUtilsLog.i("Request body: ", requestBodyString);

        return requestBodyString;
    }
}
