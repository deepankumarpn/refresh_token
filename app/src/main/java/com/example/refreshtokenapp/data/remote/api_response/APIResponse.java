package com.example.refreshtokenapp.data.remote.api_response;

import org.json.JSONException;
import org.json.JSONObject;

public class APIResponse<T> {
    private T response;
    private String errorMessage;
    private boolean error;
    public ErrorResponse errorResponse;

    public APIResponse() {
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        String message = "";
        int status = 0;
        try {
            JSONObject jsonObject = new JSONObject(errorMessage);
            message = jsonObject.getString("message");
            status = jsonObject.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.errorResponse = new ErrorResponse(status, message);
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}