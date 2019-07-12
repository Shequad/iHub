package co.za.openseseme.ihub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;

public class StartActivity
        extends AppCompatActivity {

    public static final String TAG_SCANNER = "scanner";
    public static final String TAG_CLIENT = "client";
    public static final String TAG = "tag";
    public static final String PREFS = "prefs";

    private Button btnSetScanner, btnSetClient;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        if (sharedPreferences != null) {

            if (sharedPreferences.contains(TAG)) {

                startActivity(sharedPreferences.getString(TAG, TAG_SCANNER));

            } else {

                setUp();
            }

        } else {

            setUp();

        }
    }

    private void setUp() {
        btnSetClient = findViewById(R.id.btnSetClient);
        btnSetScanner = findViewById(R.id.btnSetScanner);

        btnSetClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TAG_CLIENT);
            }
        });

        btnSetScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TAG_SCANNER);
            }
        });
    }

    private void startActivity(String tag) {

        sharedPreferences.edit().putString(TAG, tag).commit();

        FirebaseMessaging.getInstance().subscribeToTopic(tag.toLowerCase());

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(TAG, tag);
        startActivity(intent);

        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();
    }
}
