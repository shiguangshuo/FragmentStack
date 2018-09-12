package com.xiaoying.slidingwindow.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoying.fragmentstack.library.BaseFragment;

/**
 * <br/>Author：yunying.zhang
 * <br/>Email: yunyingzhang@rastar.com
 * <br/>Date: 2018/6/2
 */
public class SlidingContentFragment extends BaseFragment {

    public static SlidingContentFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt("index", index);
        SlidingContentFragment fragment = new SlidingContentFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_sliding_content, container, false);

        initView(contentView);

        return contentView;
    }

    private void initView(View contentView) {
        final TextView tvMsg = contentView.findViewById(R.id.tv_sliding_content);

        Bundle args = getArguments();
        if(null == args) {
            tvMsg.setText("Unknown page!");
            return;
        }
        final int index = args.getInt("index", 0);
        tvMsg.setText(String.format("This is the %d page", index));
    }
}
