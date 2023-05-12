package com.example.refreshtokenapp.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.refreshtokenapp.RefreshtokenApp;
import com.example.refreshtokenapp.data.remote.CommonResponse;
import com.example.refreshtokenapp.data.remote.api_response.APIResponse;
import com.example.refreshtokenapp.data.remote.login.LoginRequest;
import com.example.refreshtokenapp.data.remote.login.LoginResponse;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository loginRepository;
    private MutableLiveData<APIResponse<LoginResponse>> mutableLiveData;
    private MutableLiveData<APIResponse<CommonResponse>> mutableLiveDataOne;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        RefreshtokenApp app = (RefreshtokenApp) application;
        loginRepository = new LoginRepository(app.getAPIManager());
    }

    public LiveData<APIResponse<LoginResponse>> login(LoginRequest loginRequest) {
        mutableLiveData = loginRepository.login(loginRequest);
        return mutableLiveData;
    }

    public LiveData<APIResponse<CommonResponse>> getUserDetails() {
        mutableLiveDataOne = loginRepository.getUserDetails();
        return mutableLiveDataOne;
    }
}
