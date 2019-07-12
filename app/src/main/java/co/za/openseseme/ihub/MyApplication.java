package co.za.openseseme.ihub;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {

    private static boolean activityVisible;

    @Override
    public void onCreate() {
        super.onCreate();

        //AndroidNetworking.initialize(getApplicationContext());
        //FirebaseApp.initializeApp(this);

        // Adding an Network Interceptor for Debugging purpose :
        //OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
        //        .addNetworkInterceptor(new StethoInterceptor())
        //        .build();
        //AndroidNetworking.initialize(getApplicationContext(),okHttpClient);
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }
}
