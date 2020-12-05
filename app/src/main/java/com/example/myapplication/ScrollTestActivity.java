package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.fragments.FragmentA;
import com.example.myapplication.fragments.FragmentB;

import java.util.ArrayList;
import java.util.List;

/**
 * Sunlley
 * 2020/12/5
 * -----------------
 * 滑动演示
 */
public class ScrollTestActivity extends BaseActivity{

    NestedScrollView v_nested_container;
    RecyclerView v_top_recyclerview;
    ViewPager v_pager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_test);
        v_nested_container = findViewById(R.id.v_nested_container);
        v_pager = findViewById(R.id.v_pager);
        v_top_recyclerview = findViewById(R.id.v_top_recyclerview);
        v_top_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        v_top_recyclerview.setAdapter(new ScrollTestAdapter());

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentA());
        fragments.add(new FragmentB());
        v_pager.setAdapter(new ScrollTestPagerAdapter(getSupportFragmentManager(),fragments));

        v_nested_container.post(new Runnable() {
            @Override
            public void run() {
                int height = v_nested_container.getHeight();
                int screenHeight = Utils.getScreenHeight(ScrollTestActivity.this);
                Log.i("SSL","v_nested_container:height:"+height+"    "+screenHeight);
                ViewGroup.LayoutParams layoutParams = v_pager.getLayoutParams();
                layoutParams.height = screenHeight-Utils.dp2px(ScrollTestActivity.this,40);
                v_pager.setLayoutParams(layoutParams);
            }
        });
        v_nested_container.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int height = v.getHeight();
                int measuredHeight = v.getMeasuredHeight();
                Log.i("SSL","onScrollChange:"+measuredHeight+"    scrollY："+scrollY+"   oldScrollY:"+oldScrollY);
            }
        });
        v_nested_container.fullScroll(NestedScrollView.FOCUS_DOWN);

    }

    class ScrollTestAdapter extends RecyclerView.Adapter<ScrollTestHolder>{

        @NonNull
        @Override
        public ScrollTestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ScrollTestHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull ScrollTestActivity.ScrollTestHolder holder, int position) {
            holder.bind(position+"");
        }


        @Override
        public int getItemCount() {
            return 50;
        }
    }

    class ScrollTestHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ScrollTestHolder(@NonNull View itemView) {
            super(itemView);
            textView= (TextView) itemView;
        }
        public void bind(String text){
            textView.setText("测试"+text);
        }
    }

    class ScrollTestPagerAdapter extends FragmentPagerAdapter{
        List<Fragment> fragments;

        public ScrollTestPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
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
