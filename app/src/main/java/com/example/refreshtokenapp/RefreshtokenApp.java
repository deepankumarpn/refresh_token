package com.example.refreshtokenapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Application;
import android.util.Log;

import com.example.refreshtokenapp.data.local.SharedPreferencesManager;
import com.example.refreshtokenapp.data.remote.APIManager;
import com.example.refreshtokenapp.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RefreshtokenApp extends Application {

    private SharedPreferencesManager sharedPreferencesManager;
    private Gson gson;
    private Retrofit retrofit;
    private HttpLoggingInterceptor logging;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());
        logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.level(HttpLoggingInterceptor.Level.BASIC);
        }
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gson;
    }

    public Retrofit getRetrofit() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    okhttp3.Response response = chain.proceed(request);
                    if (response.code() == 400) {
                        return response;
                    } else if (response.code() == 401) {
                        // Get a new session token
                        String newSessionToken = getNewSessionToken(sharedPreferencesManager.getAuthJWT());
                        if (newSessionToken != null) {
                            // Add the new session token to the request header
                            Request newRequest = request.newBuilder()
                                    .header("x-access-token", newSessionToken)
                                    .build();
                            // Retry the original request with the new session token
                            return chain.proceed(newRequest);
                        }

                    } else if (response.code() == 403) {
                        sharedPreferencesManager.logoutUser();
                        return response;
                    } else if (response.code() == 404) {
                        return response;
                    } else if (response.code() == 503) {
                        return response;
                    }
                    return response;
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
        return retrofit;
    }

    public static String getBaseURL() {
        String baseUrl = Constants.BASE_URL;
        return baseUrl;
    }

    private String getNewSessionToken(String authToken) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

        /* json - body data */
        JSONObject isRefresh = new JSONObject();
        try {
            isRefresh.put("isRefreshToken", true);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, isRefresh.toString());

        // Create the request
        Request request = new Request.Builder()
                .url(RefreshtokenApp.getBaseURL() + "/v1/user/refreshToken")
                .addHeader("x-access-token", "" + authToken)
                .post(requestBody)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject data = jsonObject.getJSONObject("data");
                String sessionToken = data.getString("sessionToken");
                sharedPreferencesManager.saveJWT(sessionToken);
                Log.v(TAG, "Success new session token : " + response);
                return sessionToken;
            } else {
                int errorCode = response.code();
                switch (errorCode) {
                    case 401:
                        break;
                    case 403:
                        sharedPreferencesManager.logoutUser();
                        break;
                    case 404:
                        break;
                    case 500:
                        break;
                    default:
                        sharedPreferencesManager.logoutUser();
                        break;
                }
                Log.e(TAG, "Failed to refresh token. Error response: " + response);

            }
            response.close();
        } catch (IOException | JSONException e) {
            // Handle the exception
            Log.e(TAG, "Failed to refresh token", e);
            sharedPreferencesManager.logoutUser();
        }
        return null;
    }

    public APIManager getAPIManager() {
        if (retrofit == null) {
            retrofit = getRetrofit();
        }
        return retrofit.create(APIManager.class);
    }
}