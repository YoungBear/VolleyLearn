package com.example.volleylearn.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.volleylearn.R;

import java.util.HashMap;
import java.util.Map;

public class SimpleRequestActivity extends Activity {

    private static final String TAG = SimpleRequestActivity.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private static final String TEST_URL = "http://api.androidhive.info/volley/person_object.json";

    private TextView txtDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_request);
        initViews();

        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this.getApplicationContext());
//        simpleRequest();
        simplePostRequest();
    }

    private void initViews() {
        txtDisplay = (TextView) findViewById(R.id.txt_display);
    }

    private void simpleRequest() {

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                TEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse, response: " + response);
                        txtDisplay.setText("Response is: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse, error: " + error.getMessage());
                        txtDisplay.setText("That didn't work!");
                    }
                });
        // Set the tag on the request.
        stringRequest.setTag(TAG);

        mRequestQueue.add(stringRequest);
    }

    private void simplePostRequest() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                TEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "post, onResponse, response: " + response);
                        txtDisplay.setText("Response is: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "post, onErrorResponse, error: " + error.getMessage());
                        txtDisplay.setText("That didn't work!");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("password", "password123");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return headers;
            }
        };
        //关闭Cache
        stringRequest.setShouldCache(false);
        stringRequest.setTag(TAG);
        mRequestQueue.add(stringRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }
}
