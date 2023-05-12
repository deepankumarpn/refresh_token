package com.example.refreshtokenapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.refreshtokenapp.R;
import com.example.refreshtokenapp.data.remote.login.LoginRequest;
import com.example.refreshtokenapp.databinding.ActivityMainBinding;
import com.example.refreshtokenapp.ui.login.Login;
import com.example.refreshtokenapp.ui.login.LoginViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setListeners();
    }

    private void setListeners() {
        binding.apiCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.apiCall) {
            getUserDetails();
        }
    }

    private void getUserDetails() {
        loginViewModel.getUserDetails()
                .observe(this, apiResponse -> {
                    if (apiResponse.isError()) {
                        String errorMessage = apiResponse.errorResponse.message;
                        Toast.makeText(MainActivity.this,
                                errorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this,
                                String.valueOf(apiResponse), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}