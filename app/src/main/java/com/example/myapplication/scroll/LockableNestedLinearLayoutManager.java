package com.example.myapplication.scroll;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Sunlley
 * 2020/12/6
 * --------------
 */
public class LockableNestedLinearLayoutManager extends LinearLayoutManager implements LockableNested{
    public final String TAG = getClass().getSimpleName();
    public boolean canScroll=true;

    public LockableNestedLinearLayoutManager(Context context) {
        super(context);
    }

    public LockableNestedLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LockableNestedLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollHorizontally() {
        if (!canScroll){
            return false;
        }
        return super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        if (!canScroll){
            return false;
        }
        return super.canScrollVertically();
    }

    @Override
    public void onLocked(boolean locked) {
        canScroll=!locked;
    }


}
