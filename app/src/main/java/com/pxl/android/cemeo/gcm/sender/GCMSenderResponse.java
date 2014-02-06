package com.pxl.android.cemeo.gcm.sender;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 12:52 PM
 */
public class GCMSenderResponse {
    private final int code;
    private final String message;
    private final Throwable throwable;

    public GCMSenderResponse(int code, String message) {
        this.code = code;
        this.message = message;
        throwable = null;
    }

    public GCMSenderResponse(String message, Throwable throwable) {
        this.code = -1;
        this.message = message;
        this.throwable = throwable;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public boolean ok() {
        return code == 204;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GCMSenderResponse{code=").append(code).append(", message='").append(message).append("'}");
        return sb.toString();
    }
}
