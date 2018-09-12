package com.xiaoying.slidingwindow.demo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.xiaoying.fragmentstack.library.BaseActivity;
import com.xiaoying.fragmentstack.library.BaseFragment;

/**
 * <br/>Author：yunying.zhang
 * <br/>Email: yunyingzhang@rastar.com
 * <br/>Date: 2018/6/2
 */
public class SlidingWindowActivity extends BaseActivity {


    private int mOrientation;

    private RadioGroup mRGMenu;

    private SparseArray<? extends BaseFragment> mFragmens = new SparseArray<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent != null && intent.getBooleanExtra("fullScreen", false)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_sliding_window);

        setContainerId(R.id.fl_sliding_container);

        mRGMenu = findViewById(R.id.rg_sliding_menu);

        mOrientation = getResources().getConfiguration().orientation;

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if(null != lp) {
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            if(Configuration.ORIENTATION_PORTRAIT == mOrientation) {
                lp.width = dm.widthPixels / 5 * 4;
                lp.gravity = Gravity.END;
                lp.windowAnimations = R.style.SlidingWindowActivityAnimationFromRight;
            } else {
                lp.width = dm.widthPixels / 2;
                lp.gravity = Gravity.START;
                lp.windowAnimations = R.style.SlidingWindowActivityAnimationFromLeft;
            }
            getWindow().setAttributes(lp);
        }

        final SlidingContentFragment fragment1 = SlidingContentFragment.newInstance(1);
        final SlidingContentFragment fragment2 = SlidingContentFragment.newInstance(2);
        final SlidingContentFragment fragment3 = SlidingContentFragment.newInstance(3);
        final SlidingContentFragment fragment4 = SlidingContentFragment.newInstance(4);


        mRGMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_tab1:
                        addFragment(fragment1);
                        break;
                    case R.id.rbtn_tab2:
                        addFragment(fragment2);
                        break;
                    case R.id.rbtn_tab3:
                        addFragment(fragment3);
                        break;
                    case R.id.rbtn_tab4:
                        addFragment(fragment4);
                        break;
                    default:
                        break;
                }
            }
        });

        mRGMenu.check(R.id.rbtn_tab1);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(mOrientation != newConfig.orientation) {
            exit();
            mOrientation = newConfig.orientation;
        }
    }

    @Override
    public void onBackPressed() {
        popBack();
//        finish();

    }

    @Override
    public void finish() {
        super.finish();
        if(Configuration.ORIENTATION_PORTRAIT == mOrientation) {
            overridePendingTransition(R.anim.wa_sdk_anim_out_to_right, R.anim.wa_sdk_anim_out_to_right);
        } else {
            overridePendingTransition(R.anim.wa_sdk_anim_out_to_left, R.anim.wa_sdk_anim_out_to_left);
        }
    }

    @Override
    public void exit() {
        finish();
    }

    private void addFragment(Fragment fragment) {
        addFragmentToStackWithoutAnimation(fragment, false);
    }
}
