package com.example.myapplication.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.scroll.LockableNested;
import com.example.myapplication.scroll.LockableNestedLinearLayoutManager;

/**
 * Sunlley
 * 2020/11/27
 * -----------------
 */
public class FragmentA extends BaseFragment implements LockableNested {
    RecyclerView v_recyclerview;
    boolean locked;
    private LockableNestedLinearLayoutManager layoutManager;

    @Override
    protected int layout() {
        return R.layout.fragment_test;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v_recyclerview = view.findViewById(R.id.v_recyclerview);
        layoutManager = new LockableNestedLinearLayoutManager(getContext());
        v_recyclerview.setLayoutManager(layoutManager);
        v_recyclerview.setAdapter(new FragmentAAdapter());
        onLocked(this.locked);
    }

    @Override
    public void onLocked(boolean locked) {
        this.locked = locked;
        if (v_recyclerview != null) {
            layoutManager.onLocked(locked);
        }
    }

    class FragmentAAdapter extends RecyclerView.Adapter<FragmentAHolder>{

        @NonNull
        @Override
        public FragmentAHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FragmentAHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull FragmentAHolder holder, int position) {
            holder.bind(position+"");
        }


        @Override
        public int getItemCount() {
            return 50;
        }
    }

    class FragmentAHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public FragmentAHolder(@NonNull View itemView) {
            super(itemView);
            textView= (TextView) itemView;
        }
        public void bind(String text){
            textView.setText("FragmentAHolder:"+text);
        }
    }
    class LinearLayoutManager2 extends LinearLayoutManager{
        public LinearLayoutManager2(Context context) {
            super(context);
        }

        public LinearLayoutManager2(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public LinearLayoutManager2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }
    }

}
