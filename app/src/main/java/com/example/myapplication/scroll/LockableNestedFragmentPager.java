package com.example.myapplication.scroll;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Sunlley
 * 2020/12/5
 * --------------
 */
public class LockableNestedFragmentPager extends ViewPager implements LockableNested {
    public final String TAG = getClass().getSimpleName();

    LockedNestedPagerAdapter adapter;

    public LockableNestedFragmentPager(@NonNull Context context) {
        super(context);
    }

    public LockableNestedFragmentPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(LockedNestedPagerAdapter adapter) {
        this.adapter = adapter;
        super.setAdapter(adapter);
    }

    @Override
    public void onLocked(boolean locked) {
        if (this.adapter != null) {
            this.adapter.onLocked(locked);
        }
    }
}
