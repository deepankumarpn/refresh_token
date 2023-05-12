package com.example.refreshtokenapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.refreshtokenapp.BuildConfig;
import com.example.refreshtokenapp.R;
import com.example.refreshtokenapp.data.local.SharedPreferencesManager;
import com.example.refreshtokenapp.databinding.ActivitySplashBinding;
import com.example.refreshtokenapp.ui.login.Login;
import com.example.refreshtokenapp.utils.Utils;

public class Splash extends AppCompatActivity {
    private ActivitySplashBinding binding;

    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferencesManager = new SharedPreferencesManager(this);

        if (Utils.isEmulator() && !BuildConfig.DEBUG) {
            Splash.this.finish();
        } else {
            if (sharedPreferencesManager.getJWT().isEmpty()) {
                startActivity(new Intent(this, Login.class));
                finish();
            } else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }
    }
}