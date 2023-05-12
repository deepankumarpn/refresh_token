package com.example.refreshtokenapp.ui.login;

import androidx.lifecycle.MutableLiveData;

import com.example.refreshtokenapp.data.remote.APIManager;
import com.example.refreshtokenapp.data.remote.CommonResponse;
import com.example.refreshtokenapp.data.remote.api_response.APIResponse;
import com.example.refreshtokenapp.data.remote.login.LoginRequest;
import com.example.refreshtokenapp.data.remote.login.LoginResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private APIManager apiManager;

    public LoginRepository(APIManager apiManager) {
        this.apiManager = apiManager;
    }

    public MutableLiveData<APIResponse<LoginResponse>> login(LoginRequest loginRequest) {
        final APIResponse<LoginResponse> apiResponse = new APIResponse<>();
        final MutableLiveData<APIResponse<LoginResponse>> mutableLiveData = new MutableLiveData<>();

        Call<LoginResponse> login = apiManager.login("sms",loginRequest);
        login.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call,
                                   Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    try {
                        apiResponse.setError(true);
                        apiResponse.setErrorMessage(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    apiResponse.setResponse(response.body());
                }
                mutableLiveData.setValue(apiResponse);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                apiResponse.setError(true);
                apiResponse.setErrorMessage(t.getMessage());
                mutableLiveData.setValue(apiResponse);
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<APIResponse<CommonResponse>> getUserDetails() {
        final APIResponse<CommonResponse> apiResponse = new APIResponse<>();
        final MutableLiveData<APIResponse<CommonResponse>> mutableLiveData = new MutableLiveData<>();
        Call<CommonResponse> login = apiManager.getUserDetails();
        login.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call,
                                   Response<CommonResponse> response) {
                if (!response.isSuccessful()) {
                    try {
                        apiResponse.setError(true);
                        apiResponse.setErrorMessage(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    apiResponse.setResponse(response.body());
                }
                mutableLiveData.setValue(apiResponse);
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                apiResponse.setError(true);
                apiResponse.setErrorMessage(t.getMessage());
                mutableLiveData.setValue(apiResponse);
            }
        });
        return mutableLiveData;
    }
}
