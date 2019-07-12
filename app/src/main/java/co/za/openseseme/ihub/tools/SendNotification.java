package co.za.openseseme.ihub.tools;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import co.za.openseseme.ihub.net.MySingleton;

public class SendNotification {

    private static final String URL_SEND_NOTIFICATION = "http://openseseme.co.za/app/sendNotification.php";

    public static void send(final Context context, final String qrID, final OnResultsReturned onResultsReturned) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEND_NOTIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        onResultsReturned.onResultReturned(response);

                        /*try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            String message = jsonObject.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                onResultsReturned.onResultReturned(null);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("qrID", qrID);

                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestque(stringRequest);
    }
}
