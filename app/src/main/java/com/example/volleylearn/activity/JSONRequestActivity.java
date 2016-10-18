package com.example.volleylearn.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.volleylearn.R;
import com.example.volleylearn.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONRequestActivity extends Activity {

    public static final String TAG = JSONRequestActivity.class.getSimpleName();

    private static final String TEST_URL = "http://api.androidhive.info/volley/person_object.json";
    private static final String TEST_URL2 = "http://api.androidhive.info/volley/person_array.json";

    private TextView txtDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonrequest);
        initViews();

//        jsonRequest();
        jsonArrayRequest();
    }

    private void initViews() {
        txtDisplay = (TextView) findViewById(R.id.txt_display);
    }

    private void jsonRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                TEST_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        txtDisplay.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse, error: " + error.getMessage());
                    }
                });
        VolleyController.getInstance(this).addToRequestQueue(jsonObjectRequest, TAG);
    }

    private void jsonArrayRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                TEST_URL2,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        txtDisplay.setText(response.length() + "\n" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse, error: " + error.getMessage());
                    }
                }
        );
        VolleyController.getInstance(this).addToRequestQueue(jsonArrayRequest, TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        VolleyController.getInstance(this).cancelPendingRequests(TAG);
    }
}
