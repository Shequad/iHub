package co.za.openseseme.ihub;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.za.openseseme.ihub.net.MySingleton;
import co.za.openseseme.ihub.tools.Logg;
import co.za.openseseme.ihub.tools.OnResultsReturned;
import co.za.openseseme.ihub.tools.QuickDialog;
import co.za.openseseme.ihub.tools.SendNotification;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    public static final String MESSAGE_SUCCESS = "qr_success";
    public static final String MESSAGE_FAILIER = "qr_failed";

    Button btnScan;
    //String qrIDv;
    String url = "http://openseseme.co.za/app/checkMeetingApp.php";
    AlertDialog.Builder builder;

    private View progressBar;

    //qr code scanner object
    private IntentIntegrator qrScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        Logg.d("MainActivity", "Refreshed token:------------------------------ " + com.google.firebase.iid.FirebaseInstanceId.getInstance().getToken());

        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnScan = findViewById(R.id.btnScan);
        //initializing scan object
        qrScanner = new IntentIntegrator(this);
        builder = new AlertDialog.Builder(MainActivity.this);

        btnScan.setOnClickListener(this);

        hideLoading();
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        btnScan.setText("SCANNING...");
    }

    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        btnScan.setText("SCAN");
    }

    @Override
    public void onClick(View view) {

        showLoading();

        //initiating the qr code scan
        qrScanner.addExtra("SCAN_MODE", "QR_CODE_MODE");
        qrScanner.setCameraId(0);
        qrScanner.setPrompt("Please align your QR code with the tablet’s camera");

        qrScanner.initiateScan();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {

            if (result.getContents() == null) {
                Toast.makeText(this, "No results", Toast.LENGTH_SHORT).show();
            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkCode("1234567890");
                    }
                }, 1000);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkCode(final String qrID) {

        Toast.makeText(this, qrID, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //hideLoading();
                        Logg.d(TAG, "Code verification response: " + response);

                        try {
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = new JSONObject(response); //jsonArray.getJSONObject(0);

                            String code = jsonObject.getString("code");
                            final String message = jsonObject.getString("message");

                            QuickDialog.show(MainActivity.this, message);

                            if (code.equalsIgnoreCase(MESSAGE_SUCCESS)) {

                                SendNotification.send(getApplicationContext(), qrID, new OnResultsReturned() {
                                    @Override
                                    public void onResultReturned(String result) {

                                        hideLoading();

                                        if (result != null) {

                                            Logg.d(TAG, "Notification response: " + result);

                                            try {
                                                JSONArray jsonArray = new JSONArray(result);
                                                JSONObject jsonObject = jsonArray.getJSONObject(0);

                                                String notificationResult = jsonObject.getString("result");

                                                //if (notificationResult.equalsIgnoreCase("sent"))
                                                //    QuickDialog.show(MainActivity.this, message);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(MainActivity.this, "An error occurred. Please try again later", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                            } else {

                                hideLoading();
                            }

                            //builder.setTitle("Open Sesame Response");
                            //builder.setMessage(message);
                            //DisplayAlert(code);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideLoading();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hideLoading();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("qrID", qrID);

                return params;
            }
        };
        MySingleton.getInstance(MainActivity.this).addToRequestque(stringRequest);
    }

    private void DisplayAlert(String code) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (Build.VERSION.SDK_INT >= 11) {
                    recreate();
                } else {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
