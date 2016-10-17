package com.example.volleylearn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.volleylearn.activity.ImageRequestActivity;
import com.example.volleylearn.activity.SimpleRequestActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        Button btnSimple = (Button) findViewById(R.id.btn_simple_request);
        Button btnImage = (Button) findViewById(R.id.btn_image_request);

        btnImage.setOnClickListener(btnClickListener);
        btnSimple.setOnClickListener(btnClickListener);
    }

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_simple_request:
                    startActivity(SimpleRequestActivity.class);
                    break;
                case R.id.btn_image_request:
                    startActivity(ImageRequestActivity.class);
                    break;
                default:
                    break;
            }

        }
    };

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }
}
