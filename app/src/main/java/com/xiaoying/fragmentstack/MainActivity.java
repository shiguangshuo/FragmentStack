package com.xiaoying.fragmentstack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.xiaoying.fragmentstack.library.BaseActivity;

public class MainActivity extends BaseActivity {

    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 设置容器id
        setContainerId(R.id.fl_container);

        mTvTitle = findViewById(R.id.tv_title);

        addFragmentToStackWithoutAnimation(TestFragment.newInstance(null), true);
    }

    public void setTitle(CharSequence title) {
        mTvTitle.setText(title);
    }


    @Override
    public void onBackPressed() {
        // 重写onBackPressed方法
        popBack();
    }

    @Override
    public void exit() {
        // 重写exit方法
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Exit app?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
