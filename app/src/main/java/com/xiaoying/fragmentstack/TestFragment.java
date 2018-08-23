package com.xiaoying.fragmentstack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

/**
 * <br/>Author：yunying.zhang
 * <br/>Email: yunyingzhang@rastar.com
 * <br/>Date: 2018/8/23
 */
public class TestFragment extends BaseFragment {

    private Button mBtnHome;
    private Button mBtnPrevious;
    private Button mBtnNext;

    private TextView mTvMsg;

    private int mPageNum = 0;

    public static TestFragment newInstance(Bundle args) {
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if(null != args) {
            mPageNum += args.getInt("extra_page", 0);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_test, container, false);

        mBtnHome = contentView.findViewById(R.id.btn_home);
        mBtnPrevious = contentView.findViewById(R.id.btn_previous);
        mBtnNext = contentView.findViewById(R.id.btn_next);
        mTvMsg = contentView.findViewById(R.id.tv_msg);

        mBtnHome.setOnClickListener(this);
        mBtnPrevious.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);

        mTvMsg.setText(String.format(Locale.getDefault(), "This is page %d", mPageNum));
        setTitle(String.format(Locale.getDefault(), "Page %d", mPageNum));
        Log.e("TestFragment", "" + this.hashCode() + " onCreateView---" +  mPageNum);

        return contentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("TestFragment", "" + this.hashCode() + " onDestroyView--" +  mPageNum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                backToHome();
                break;
            case R.id.btn_previous:
                back();
                break;
            case R.id.btn_next:
                Bundle args = new Bundle();
                args.putInt("extra_page", mPageNum + 1);
                openNewFragmentWithoutAnimation(TestFragment.newInstance(args));
                break;
            default:
                break;
        }
    }

    private void setTitle(String title) {
        Activity activity = getActivity();
        if(null != activity && activity instanceof MainActivity) {
            ((MainActivity) activity).setTitle(title);
        }
    }
}
