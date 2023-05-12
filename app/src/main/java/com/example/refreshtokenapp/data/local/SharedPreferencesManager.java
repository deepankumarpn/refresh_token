package com.example.refreshtokenapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.example.refreshtokenapp.utils.Constants;
import com.example.refreshtokenapp.utils.Utils;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class SharedPreferencesManager {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferencesManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public void saveJWT(String jwt) {
        sharedPreferences.edit().putString(Constants.JWT, jwt).apply();
    }

    public String getJWT() {
        return sharedPreferences.getString(Constants.JWT, "");
    }

    public void saveAuthJWT(String refreshJWT) {
        sharedPreferences.edit().putString(Constants.REFRESH_JWT, refreshJWT).apply();
    }

    public String getAuthJWT() {
        return sharedPreferences.getString(Constants.REFRESH_JWT, "");
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        sharedPreferences.edit().clear().apply();
        Utils.loginAgain(context);
    }

    public void clear() {
        // Clearing all data from Shared Preferences
        sharedPreferences.edit().clear().apply();
    }


}