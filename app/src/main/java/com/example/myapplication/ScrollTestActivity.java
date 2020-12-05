package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.fragments.FragmentA;
import com.example.myapplication.fragments.FragmentB;
import com.example.myapplication.scroll.LockableNestedScrollView;
import com.example.myapplication.scroll.LockableNestedFragmentPager;
import com.example.myapplication.scroll.LockedNestedPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Sunlley
 * 2020/12/5
 * -----------------
 * 滑动演示
 */
public class ScrollTestActivity extends BaseActivity {

    public String TAG = "tESTSSL";
    LockableNestedScrollView v_nested_container;
    LockableNestedFragmentPager v_pager;
    RecyclerView v_top_recyclerview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_test);
        v_nested_container = findViewById(R.id.v_nested_container);
        v_pager = findViewById(R.id.v_pager);
        v_top_recyclerview = findViewById(R.id.v_top_recyclerview);
        v_top_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        v_top_recyclerview.setAdapter(new ScrollTestAdapter());
//        v_top_recyclerview.setNestedScrollingEnabled(true);

//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v_top_recyclerview.getLayoutParams();
//        layoutParams.height=0;
//        v_top_recyclerview.setLayoutParams(layoutParams);
        v_nested_container.setNestedScrollingEnabled(true);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentA());
        fragments.add(new FragmentB());
        v_pager.setAdapter(new LockedNestedPagerAdapter(getSupportFragmentManager(), fragments));
        v_pager.onLocked(true);
//        v_nested_container.setScrollingEnabled(false);
        v_nested_container.post(new Runnable() {
            @Override
            public void run() {
                int height = v_nested_container.getHeight();
                int screenHeight = Utils.getScreenHeight(ScrollTestActivity.this);
                Log.i("tESTSSL", "v_nested_container:height:" + height + "    " + v_nested_container.getScrollY() + "    " + screenHeight);
                ViewGroup.LayoutParams layoutParams = v_pager.getLayoutParams();
                layoutParams.height = screenHeight - Utils.dp2px(ScrollTestActivity.this, 40);
                v_pager.setLayoutParams(layoutParams);
            }
        });
//        v_nested_container.setNestedScrollingEnabled(true);
        v_nested_container.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int height = v.getHeight();
                int measuredHeight = v.getMeasuredHeight();
                int scrollY1 = v.getScrollY();
                int[] location = new int[2];
                int measuredHeight1 = v.getChildAt(0).getMeasuredHeight();
                Log.i("tESTSSL", "measuredHeight1:" + (measuredHeight1) + "       measuredHeight1:" + (measuredHeight1 - height) + "    scrollY：" + scrollY + "   oldScrollY:" + oldScrollY + "    scrollY1.y:" + scrollY1);
                if (measuredHeight1 - height == scrollY) {
                    Log.i("tESTSSL", "onScrollChange: 到底了");
                    v_top_recyclerview.post(new Runnable() {
                        @Override
                        public void run() {
                            v_pager.onLocked(false);
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v_top_recyclerview.getLayoutParams();
                            layoutParams.height=0;
                            v_top_recyclerview.setLayoutParams(layoutParams);
//                            ViewGroup.LayoutParams layoutParams1 = v.getChildAt(0).getLayoutParams();
//                            layoutParams1.height = height;
//                            v.getChildAt(0).setLayoutParams(layoutParams1);
//                            v_nested_container.requestLayout();
                        }
                    });
                }
            }
        });
//        v_nested_container.fullScroll(NestedScrollView.FOCUS_DOWN);

    }

    class ScrollTestAdapter extends RecyclerView.Adapter<ScrollTestHolder> {

        @NonNull
        @Override
        public ScrollTestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ScrollTestHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull ScrollTestActivity.ScrollTestHolder holder, int position) {
            holder.bind(position + "");
        }


        @Override
        public int getItemCount() {
            return 50;
        }
    }

    class ScrollTestHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ScrollTestHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        public void bind(String text) {
            textView.setText("测试" + text);
        }
    }

}
