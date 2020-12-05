package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Sunlley
 * 2020/11/27
 * -----------------
 */
public class FragmentInner extends BaseFragment{

    TabLayout v_tablayout;
    ViewPager mViewPager;

    List<BaseFragment> fragments;

    @Override
    protected int layout() {
        return R.layout.fragment_inner;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v_tablayout=view.findViewById(R.id.v_tablayout);
        mViewPager=view.findViewById(R.id.v_pager);
        initTab();
        initPage();
    }

    private void initTab() {
        v_tablayout.addTab( v_tablayout.newTab().setText("FragmentA"));
        v_tablayout.addTab( v_tablayout.newTab().setText("FragmentB"));
        v_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                changePage(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void initPage(){
        fragments = new ArrayList<>();
        fragments.add(new FragmentInnerA());
        fragments.add(new FragmentInnerB());

        mViewPager.setAdapter(new NavAdapter(getChildFragmentManager(),fragments));

    }
    private void changePage(int index){
        mViewPager.setCurrentItem(index);
    }


    static class NavAdapter extends FragmentPagerAdapter {
        private final List<BaseFragment> fragments;

        public NavAdapter(@NonNull FragmentManager fm, List<BaseFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
