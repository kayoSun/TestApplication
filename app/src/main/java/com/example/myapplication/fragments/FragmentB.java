package com.example.myapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

/**
 * Sunlley
 * 2020/11/27
 * -----------------
 */
public class FragmentB extends BaseFragment{

    @Override
    protected int layout() {
        return R.layout.fragment_testb;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = getView().findViewById(R.id.v_test);
        textView.setText(TAG);
        textView.setBackgroundColor(Color.BLUE);
    }
}
