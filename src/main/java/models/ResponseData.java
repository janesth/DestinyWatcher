package models;

import models.profile.ResponseProfile;

public class ResponseData {
    private int ErrorCode;
    private String ErrorStatus;
    private String Message;
    private ResponseProfile Response;
    private int ThrottleSeconds;

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorStatus() {
        return ErrorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        ErrorStatus = errorStatus;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ResponseProfile getResponse() {
        return Response;
    }

    public void setResponse(ResponseProfile response) {
        Response = response;
    }

    public int getThrottleSeconds() {
        return ThrottleSeconds;
    }

    public void setThrottleSeconds(int throttleSeconds) {
        ThrottleSeconds = throttleSeconds;
    }
}
