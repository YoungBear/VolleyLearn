package com.example.volleylearn.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.volleylearn.R;
import com.example.volleylearn.volley.VolleyController;

public class ImageRequestActivity extends Activity {
    private static final String TAG = ImageRequestActivity.class.getSimpleName();

    private ImageView mImageView, mImageView3;
    private NetworkImageView mNetworkImageView;

    private static final String TEST_URL_1 = "http://i.imgur.com/CqmBjo5.jpg";
    private static final String TEST_URL_2 = "http://i.imgur.com/zkaAooq.jpg";
    private static final String TEST_URL_3 = "http://imgur.com/0gqnEaY.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_request);

        initViews();
        imageRequest();
        setNetworkImageView();
        imageLoaderGet();
    }

    private void initViews() {
        mImageView = (ImageView) findViewById(R.id.img_display);

        mNetworkImageView = (NetworkImageView) findViewById(R.id.network_img_display);
        mImageView3 = (ImageView) findViewById(R.id.img_display3);
    }

    private void imageRequest() {

        ImageRequest imageRequest = new ImageRequest(
                TEST_URL_1,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        mImageView.setImageBitmap(response);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse, error: " + error.getMessage());
                    }
                }
        );
        VolleyController.getInstance(this).addToRequestQueue(imageRequest, TAG);
    }


    private void setNetworkImageView() {
        ImageLoader imageLoader = VolleyController.getInstance(this).getImageLoader();
        mNetworkImageView.setImageUrl(TEST_URL_2, imageLoader);
    }

    private void imageLoaderGet() {
        ImageLoader imageLoader = VolleyController.getInstance(this).getImageLoader();
        imageLoader.get(TEST_URL_3, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                mImageView3.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        VolleyController.getInstance(this).cancelPendingRequests(TAG);
    }
}
