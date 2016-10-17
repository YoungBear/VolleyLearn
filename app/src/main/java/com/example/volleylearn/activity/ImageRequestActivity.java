package com.example.volleylearn.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.volleylearn.R;

public class ImageRequestActivity extends Activity {
    private static final String TAG = ImageRequestActivity.class.getSimpleName();

    private ImageView mImageView;
    private NetworkImageView mNetworkImageView;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static final String TEST_URL_1 = "http://i.imgur.com/CqmBjo5.jpg";
    private static final String TEST_URL_2 = "http://i.imgur.com/zkaAooq.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_request);

        initViews();
        imageRequest();
        setNetworkImageView();
    }

    private void initViews() {
        mImageView = (ImageView) findViewById(R.id.img_display);
        mNetworkImageView = (NetworkImageView) findViewById(R.id.network_img_display);
    }

    private void imageRequest() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this.getApplicationContext());
        }
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
        imageRequest.setTag(TAG);
        mRequestQueue.add(imageRequest);
    }

    private void setNetworkImageView() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this.getApplicationContext());
        }
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(mRequestQueue,
                    new LruBitmapCache());
        }
        mNetworkImageView.setImageUrl(TEST_URL_2, mImageLoader);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

    private static class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
        public static int getDefaultLruCacheSize() {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;

            return cacheSize;
        }

        public LruBitmapCache() {
            this(getDefaultLruCacheSize());
        }

        public LruBitmapCache(int sizeInKiloBytes) {
            super(sizeInKiloBytes);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight() / 1024;
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }
    }
}
