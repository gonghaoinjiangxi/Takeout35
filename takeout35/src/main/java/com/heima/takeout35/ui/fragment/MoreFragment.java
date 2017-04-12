package com.heima.takeout35.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heima.takeout35.R;

/**
 * Created by lidongzhi on 2017/4/12.
 */

public class MoreFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.fragment_, null);
        TextView textView = (TextView) rootView.findViewById(R.id.tv);
        textView.setText("更多");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        return rootView;
    }

    
}
