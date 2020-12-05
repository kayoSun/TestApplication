package com.example.myapplication.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * Sunlley
 * 2020/12/5
 * --------------
 */
public class LockableNestedScrollView extends NestedScrollView {
    public final String TAG = getClass().getSimpleName();
    private boolean scrollable = true;

    public LockableNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public LockableNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LockableNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollable){
            return true;
        }
        return scrollable && super.onTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!scrollable){
            return true;
        }
        return scrollable && super.onInterceptTouchEvent(ev);
//        return true;
    }

    public void setScrollingEnabled(boolean enabled) {
        scrollable = enabled;
    }

}
