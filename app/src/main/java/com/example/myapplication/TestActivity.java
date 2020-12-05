package com.example.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.fragments.BaseFragment;
import com.example.myapplication.fragments.FragmentA;
import com.example.myapplication.fragments.FragmentB;
import com.example.myapplication.fragments.FragmentXController;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Sunlley
 * 2020/11/27
 * -----------------
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initPage();
    }

    private void initPage(){
        FragmentA fragment = new FragmentA();
//        FragmentXController.newInstance(this).add(fragment,R.id.v_container).show(fragment).commitAllowingStateLoss();
        FragmentXController.newInstance(this).add(fragment,R.id.v_container).show(fragment).commit();

//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.v_container, fragment)
//                .show(fragment)
//                .commitAllowingStateLoss();
    }

}
