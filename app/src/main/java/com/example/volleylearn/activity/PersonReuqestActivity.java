package com.example.volleylearn.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.volleylearn.R;
import com.example.volleylearn.model.Person;
import com.example.volleylearn.volley.GsonRequest;
import com.example.volleylearn.volley.VolleyController;

public class PersonReuqestActivity extends Activity {

    private static final String TAG = PersonReuqestActivity.class.getSimpleName();

    private static final String TEST_URL = "http://api.androidhive.info/contacts/";

    private TextView txtDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_reuqest);

        initViews();
        personRequest();
    }

    private void initViews() {
        txtDisplay = (TextView) findViewById(R.id.txt_display);
    }

    private void personRequest() {
        GsonRequest<Person> gsonRequest = new GsonRequest<Person>(
                TEST_URL,
                Person.class,
                null,
                new Response.Listener<Person>() {
                    @Override
                    public void onResponse(Person response) {
                        txtDisplay.setText("size: " + response.getContacts().size());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse, error: " + error.getMessage());
                    }
                }
        );
        VolleyController.getInstance(this).addToRequestQueue(gsonRequest, TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        VolleyController.getInstance(this).cancelPendingRequests(TAG);
    }
}
