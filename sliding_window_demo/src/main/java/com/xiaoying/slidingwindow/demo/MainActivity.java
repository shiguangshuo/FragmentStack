package com.xiaoying.slidingwindow.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_text_click:
                Intent intent = new Intent(MainActivity.this, SlidingWindowActivity.class);
                if((getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
                    intent.putExtra("fullScreen", true);
                }
                startActivityForResult(intent, 1000);
                break;
            default:
                break;
        }
    }
}
