package com.example.refreshtokenapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.refreshtokenapp.R;
import com.example.refreshtokenapp.data.remote.login.LoginRequest;
import com.example.refreshtokenapp.databinding.ActivityLoginBinding;
import com.example.refreshtokenapp.ui.MainActivity;
import com.example.refreshtokenapp.utils.Utils;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setListeners();

    }

    private void setListeners() {
        binding.submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.submit) {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setPassword(binding.password.getText().toString());
            loginRequest.setEmail(binding.email.getText().toString());
            login(loginRequest);
        }
    }

    private void login(LoginRequest loginRequest) {
        loginViewModel.login(loginRequest)
                .observe(this, apiResponse -> {
                    if (apiResponse.isError()) {
                        String errorMessage = apiResponse.errorResponse.message;
                        Toast.makeText(Login.this,
                                errorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(this, MainActivity.class));
                    }
                });
    }
}