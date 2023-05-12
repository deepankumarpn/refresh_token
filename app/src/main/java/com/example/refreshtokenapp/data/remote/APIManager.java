package com.example.refreshtokenapp.data.remote;

import com.example.refreshtokenapp.data.remote.login.LoginRequest;
import com.example.refreshtokenapp.data.remote.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIManager {

    @POST("/v1/security/user/login-request/{via}/otp")
    Call<LoginResponse> login(@Path("via") String via, @Body LoginRequest loginRequest);

    @GET("/app/api/v1/security-app/user/details")
    Call<CommonResponse> getUserDetails();
}
